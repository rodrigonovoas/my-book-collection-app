package app.rodrigonovoa.mybookcollection.ui.myBooks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import app.rodrigonovoa.mybookcollection.databinding.FragmentMyBooksBinding
import app.rodrigonovoa.mybookcollection.ui.bookBrowser.BookBrowserActivity
import app.rodrigonovoa.mybookcollection.ui.dialogs.DbBookDetailDialog
import app.rodrigonovoa.mybookcollection.ui.dialogs.RemoveDialog
import app.rodrigonovoa.mybookcollection.utils.SnackBarUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyBooksFragment : Fragment() {

    private val viewModel: MyBooksViewModel by viewModel()
    private var _binding: FragmentMyBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiBookDetailDialog: DbBookDetailDialog
    private lateinit var removeDialog: RemoveDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiBookDetailDialog = DbBookDetailDialog(requireContext())
        removeDialog = RemoveDialog(requireContext())

        removeDialog.onItemClick = {
            viewModel.removeBookFromLocalDb(it)
        }

        viewListeners()
        viewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        getBooksFromLocalDb()
    }

    private fun getBooksFromLocalDb() {
        viewModel.getBookList()
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

    private fun viewModelObservers() {
        viewModel.localDbBooks.observe(viewLifecycleOwner) { it ->
            val adapter = MyBooksListAdapter(it)
            binding.rcBookList.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rcBookList.adapter = adapter

            adapter.onItemClick = {
                apiBookDetailDialog.openBookDetailDialog(it)
            }

            adapter.onLongItemClick = {
                removeDialog.openRemoveDialog(it)
            }
        }

        viewModel.afterBookRemoved.observe(viewLifecycleOwner) { it ->
            if (it == BookRemovedState.REMOVED) {
                SnackBarUtils.showPositiveMessage(binding.root, "Book deleted")
                getBooksFromLocalDb()
            } else if (it == BookRemovedState.NOT_REMOVED) {
                SnackBarUtils.showNegativeMessage(binding.root, "This book can't be deleted -- it has records!")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}