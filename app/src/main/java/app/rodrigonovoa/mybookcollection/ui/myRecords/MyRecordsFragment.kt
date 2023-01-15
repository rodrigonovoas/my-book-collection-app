package app.rodrigonovoa.mybookcollection.ui.myRecords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyRecordsBinding

class MyRecordsFragment : Fragment() {

    private lateinit var viewModel: MyRecordsViewModel
    private var _binding: FragmentMyRecordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(MyRecordsViewModel::class.java)
        _binding = FragmentMyRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerviewAdapter()
    }

    private fun setRecyclerviewAdapter() {
        val fakeData = viewModel.getRecordsFromDb()
        val adapter = MyRecordsListAdapter(fakeData)
        binding.rcRecordsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rcRecordsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}