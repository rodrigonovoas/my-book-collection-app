package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import app.rodrigonovoa.mybookcollection.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AddRecordActivity : AppCompatActivity() {
    private val viewModel: AddRecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        val spSelectedBook = findViewById<Spinner>(R.id.sp_selected_book)
        val btnAdd = findViewById<Button>(R.id.btn_add)

        btnAdd.setOnClickListener { viewModel.insertNewRecord(10000) }

        spSelectedBook.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Timber.i("book: ${viewModel.localDbBooks.value?.get(position)}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        viewModel.localDbBooks.observe(this) { it ->
            val customDropDownAdapter = SpinnerCustomAdapter(this, it)
            spSelectedBook.adapter = customDropDownAdapter
        }
    }
}