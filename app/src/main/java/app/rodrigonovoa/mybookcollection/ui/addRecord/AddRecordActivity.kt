package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import app.rodrigonovoa.mybookcollection.databinding.ActivityAddRecordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecordBinding
    private val viewModel: AddRecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewListeners()
        observeBooksFromDatabase()
    }

    private fun observeBooksFromDatabase() {
        viewModel.localDbBooks.observe(this) { it ->
            val customDropDownAdapter = SpinnerCustomAdapter(this, it)
            binding.spSelectedBook.adapter = customDropDownAdapter
        }
    }

    private fun viewListeners() {
        binding.btnAdd.setOnClickListener {
            if (viewModel.selectedBookId == 0) { return@setOnClickListener }
            viewModel.insertNewRecord(
                binding.edtSpentTimeHours.text.toString().toLong(), binding.edtSpentTimeMinutes.text.toString().toLong()
            )
        }

        binding.btnClose.setOnClickListener { finish() }

        // set selected book for viewModel
        binding.spSelectedBook.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                viewModel.selectedBookId = viewModel.localDbBooks.value?.get(position)?.id ?: 0
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        // limit minutes to 59
        binding.edtSpentTimeMinutes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().toInt() > 59) {
                    binding.edtSpentTimeMinutes.setText(0)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }
}