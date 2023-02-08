package app.rodrigonovoa.mybookcollection.ui.myRecords

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
import app.rodrigonovoa.mybookcollection.utils.SnackBarUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyRecordsFragment : Fragment() {

    companion object {
        val RESULT_CODE = 123
        val SHOW_SNACKBAR_CODE = "showRecordInsertedSnackbar"
    }

    private val viewModel: MyRecordsViewModel by viewModel()
    private var _binding: FragmentMyRecordsBinding? = null
    private val binding get() = _binding!!

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
        getRecordsFromLocalDb()

        viewModelObservers()
        viewListeners()
    }

    private fun viewListeners() {
        binding.btnAddRecord.setOnClickListener { openAddRecordActivity() }
    }

    private fun viewModelObservers() {
        viewModel.storedRecords.observe(viewLifecycleOwner) { it ->
            setRecyclerviewAdapter(it)
        }
    }

    private fun getRecordsFromLocalDb() {
        viewModel.getRecordsFromDb()
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