package app.rodrigonovoa.mybookcollection.ui.myStats

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyStatsBinding
import app.rodrigonovoa.mybookcollection.ui.addRecord.SpinnerCustomAdapter
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MyStatsFragment : Fragment() {

    private val viewModel: MyStatsViewModel by viewModel()
    private var _binding: FragmentMyStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCurrentDateInTextView()
        observables()
        viewListeners()
    }

    private fun viewListeners() {
        binding.spSelectedBook.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                binding.chart.setModel(entryModelOf(listOf()))
                val bookId = viewModel.localDbBooks.value?.get(position)?.id ?: 0
                viewModel.getCurrentWeekHoursByBookId(Calendar.getInstance(), bookId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.imvSelectDate.setOnClickListener {
            openCalendar()
        }
    }

    private fun observables() {
        viewModel.hoursPerWeek.observe(viewLifecycleOwner) { it ->
            if (it.size < 7) return@observe
            val list = mutableListOf<FloatEntry>()
            var cont = 1
            it.forEach {
                list.add(FloatEntry(cont.toFloat(), it))
                cont++
            }
            val entryModel = entryModelOf(list)
            val chart = binding.chart
            chart.setModel(entryModel)
        }

        viewModel.localDbBooks.observe(viewLifecycleOwner) { it ->
            val customDropDownAdapter = SpinnerCustomAdapter(requireContext(), it)
            binding.spSelectedBook.adapter = customDropDownAdapter
        }
    }

    private fun setCurrentDateInTextView(){
        val currentDate = DateUtils.getCurrentDateAsString()
        binding.tvSelectedDate.setText(currentDate)
    }

    private fun openCalendar() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val bookId = viewModel.localDbBooks.value?.get(0)?.id ?: 0

            val calendar = Calendar.getInstance(Locale.FRENCH)
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.YEAR, year);

            viewModel.getCurrentWeekHoursByBookId(calendar, bookId)
        }, year, month, day)

        dpd.show()
    }
}