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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyRecordsFragment : Fragment() {

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
        viewModel.getRecordsFromDb()

        viewModel.storedRecords.observe(viewLifecycleOwner) { it ->
            setRecyclerviewAdapter(it)
        }

        binding.btnAddRecord.setOnClickListener { openAddRecordActivity() }
    }

    private fun openAddRecordActivity() {
        requireContext().startActivity(
            Intent(
                context,
                AddRecordActivity::class.java
            )
        )
    }

    private fun setRecyclerviewAdapter(records: List<Record>) {
        val adapter = MyRecordsListAdapter(records)
        binding.rcRecordsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rcRecordsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}