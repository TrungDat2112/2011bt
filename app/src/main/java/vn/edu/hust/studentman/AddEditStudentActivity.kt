package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)

        val name = intent.getStringExtra(MainActivity.EXTRA_STUDENT_NAME)
        val id = intent.getStringExtra(MainActivity.EXTRA_STUDENT_ID)

        editName.setText(name)
        editId.setText(id)

        btnSave.setOnClickListener {
            val resultIntent = intent.apply {
                putExtra(MainActivity.EXTRA_STUDENT_NAME, editName.text.toString())
                putExtra(MainActivity.EXTRA_STUDENT_ID, editId.text.toString())
                putExtra(MainActivity.EXTRA_POSITION, intent.getIntExtra(MainActivity.EXTRA_POSITION, -1))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
