package app.rodrigonovoa.mybookcollection.ui.myBooks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyBooksBinding
import app.rodrigonovoa.mybookcollection.ui.bookBrowser.BookBrowserActivity

class MyBooksFragment : Fragment() {

    private lateinit var viewModel: MyBooksViewModel
    private var _binding: FragmentMyBooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(MyBooksViewModel::class.java)
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerviewAdapter()
        viewListeners()
    }

    private fun viewListeners() {
        binding.btnAddBook.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    context,
                    BookBrowserActivity::class.java
                )
            )
        }
    }

    private fun setRecyclerviewAdapter() {
        val fakeData = viewModel.getBookList()
        val adapter = MyBooksListAdapter(fakeData)
        binding.rcBookList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcBookList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}