package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import app.rodrigonovoa.mybookcollection.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRecordActivity : AppCompatActivity() {
    private val viewModel: AddRecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        val spSelectedBook = findViewById<Spinner>(R.id.sp_selected_book)
        val edtSpentHours = findViewById<EditText>(R.id.edt_spent_time_hours)
        val edtSpentMinutes = findViewById<EditText>(R.id.edt_spent_time_minutes)
        val btnAdd = findViewById<Button>(R.id.btn_add)

        btnAdd.setOnClickListener {
            if (viewModel.selectedBookId == 0) { return@setOnClickListener }
            viewModel.insertNewRecord(
                edtSpentHours.text.toString().toLong(), edtSpentMinutes.text.toString().toLong()
            )
        }

        spSelectedBook.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                viewModel.selectedBookId = viewModel.localDbBooks.value?.get(position)?.id ?: 0
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        edtSpentMinutes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().toInt() > 59) {
                    edtSpentMinutes.setText(0)
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

        viewModel.localDbBooks.observe(this) { it ->
            val customDropDownAdapter = SpinnerCustomAdapter(this, it)
            spSelectedBook.adapter = customDropDownAdapter
        }
    }
}