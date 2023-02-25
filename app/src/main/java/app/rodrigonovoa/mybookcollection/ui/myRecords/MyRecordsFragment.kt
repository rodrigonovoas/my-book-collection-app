package app.rodrigonovoa.mybookcollection.ui.myRecords

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyRecordsBinding
import app.rodrigonovoa.mybookcollection.ui.addRecord.AddRecordActivity
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import app.rodrigonovoa.mybookcollection.utils.SnackBarUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MyRecordsFragment : Fragment() {

    companion object {
        val RESULT_CODE = 123
        val SHOW_SNACKBAR_CODE = "showRecordInsertedSnackbar"
    }

    private val viewModel: MyRecordsViewModel by viewModel()
    private var _binding: FragmentMyRecordsBinding? = null
    private val binding get() = _binding!!
    private var dateCalendar: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCurrentDayInTextView()
        viewModelObservers()
        viewListeners()
    }

    override fun onResume() {
        super.onResume()
        if(dateCalendar != null) {
            viewModel.getRecordsFromInterval(dateCalendar!!)
        } else {
            getCurrentDayRecords()
        }
    }

    private fun getCurrentDayRecords() {
        val calendar = Calendar.getInstance()
        getRecordsFromLocalDbByInterval(calendar)
    }

    private fun viewListeners() {
        binding.btnAddRecord.setOnClickListener { openAddRecordActivity() }

        binding.tvRecorsDate.setOnClickListener { openCalendar() }
    }

    private fun viewModelObservers() {
        viewModel.storedRecords.observe(viewLifecycleOwner) { it ->
            setRecyclerviewAdapter(it)
        }
    }

    private fun setCurrentDayInTextView(){
        val currentDate = DateUtils.getCurrentDateAsString()
        binding.tvRecorsDate.setText(currentDate)
    }

    private fun getRecordsFromLocalDbByInterval(calendar: Calendar) {
        viewModel.getRecordsFromInterval(calendar)
    }

    private fun openAddRecordActivity() {
        startActivityForResult(
            Intent(
                context,
                AddRecordActivity::class.java
            ),
            MyRecordsFragment.RESULT_CODE
        )
    }

    private fun setRecyclerviewAdapter(records: List<Record>) {
        val adapter = MyRecordsListAdapter(records)
        binding.rcRecordsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rcRecordsList.adapter = adapter
    }

    private fun openCalendar() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance(Locale.FRENCH)
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.YEAR, year);

            dateCalendar = calendar
            binding.tvRecorsDate.setText(DateUtils.fromCalendarToString(calendar))
            viewModel.getRecordsFromInterval(calendar)
        }, year, month, day)

        dpd.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == MyRecordsFragment.RESULT_CODE) {
            val showSnackbar = data?.getBooleanExtra(MyRecordsFragment.SHOW_SNACKBAR_CODE
                , false) ?: false
            if (showSnackbar) SnackBarUtils.showPositiveMessage(binding.root, "Record added")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}