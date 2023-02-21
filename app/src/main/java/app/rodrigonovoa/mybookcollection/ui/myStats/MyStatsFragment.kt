package app.rodrigonovoa.mybookcollection.ui.myStats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyStatsBinding
import app.rodrigonovoa.mybookcollection.ui.addRecord.SpinnerCustomAdapter
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        viewModel.hoursPerWeek.observe(viewLifecycleOwner) { it ->
            if (it.size < 7) return@observe
            val list = mutableListOf<FloatEntry>()
            var cont = 0
            it.forEach {
                list.add(FloatEntry(cont.toFloat(),it))
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

        // set selected book for viewModel
        binding.spSelectedBook.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val bookId = viewModel.localDbBooks.value?.get(position)?.id ?: 0
                viewModel.getCurrentWeekHoursByBookId(bookId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }
}