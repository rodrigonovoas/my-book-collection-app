package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import app.rodrigonovoa.mybookcollection.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRecordActivity : AppCompatActivity() {
    private val viewModel: AddRecordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        val btnAdd = findViewById<Button>(R.id.btn_add)
        btnAdd.setOnClickListener { viewModel.insertNewRecord(10000) }
    }
}