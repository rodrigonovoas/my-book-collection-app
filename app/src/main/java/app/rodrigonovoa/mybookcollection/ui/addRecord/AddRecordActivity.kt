package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import app.rodrigonovoa.mybookcollection.databinding.ActivityAddRecordBinding
import app.rodrigonovoa.mybookcollection.ui.myRecords.MyRecordsFragment
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import app.rodrigonovoa.mybookcollection.utils.SnackBarUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecordBinding
    private val viewModel: AddRecordViewModel by viewModel()
    private var recordDate = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setRecordDate()
        viewListeners()
        observeBooksFromDatabase()
        observeDatabaseInsertResult()
    }

    private fun setRecordDate() {
        val date = intent.getLongExtra("recordDay", 0L)
        recordDate = date
        if(date != 0L) binding.tvRecordDate.setText(DateUtils.fromTimestampToDateString(date))
    }

    private fun observeDatabaseInsertResult() {
        viewModel.addRecord.observe(this) { it ->
            if (it == RecordAddedStatus.ADDED) {
                setShowRecordInsertedSnackbar()
                finish()
            } else if (it == RecordAddedStatus.FAIL) {
                SnackBarUtils.showNegativeMessage(binding.root, "Fail adding record")
            } else if (it == RecordAddedStatus.NO_TIME_ADDED) {
                SnackBarUtils.showNegativeMessage(binding.root, "Time not added")
            } else if (it == RecordAddedStatus.NO_BOOK_ADDED) {
                SnackBarUtils.showNegativeMessage(binding.root, "Time not added")
            }
        }
    }

    private fun setShowRecordInsertedSnackbar() {
        val intent = Intent()
        intent.putExtra(MyRecordsFragment.SHOW_SNACKBAR_CODE, true)
        setResult(MyRecordsFragment.RESULT_CODE, intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun observeBooksFromDatabase() {
        viewModel.localDbBooks.observe(this) { it ->
            val customDropDownAdapter = SpinnerCustomAdapter(this, it)
            binding.spSelectedBook.adapter = customDropDownAdapter
        }
    }

    private fun viewListeners() {
        binding.btnAdd.setOnClickListener {
            var spentTimeHours = binding.edtSpentTimeHours.text.toString()
            var spentTimeMinutes = binding.edtSpentTimeMinutes.text.toString()
            if (spentTimeHours.isEmpty()) spentTimeHours = "0"
            if (spentTimeMinutes.isEmpty()) spentTimeMinutes = "0"

            viewModel.insertNewRecord(
                recordDate, spentTimeHours.toLong(), spentTimeMinutes.toLong()
            )
        }

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