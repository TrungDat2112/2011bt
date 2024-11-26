package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  private lateinit var studentAdapter: ArrayAdapter<StudentModel>
  private lateinit var listView: ListView
  private var selectedStudentPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listView = findViewById(R.id.list_view_students)
    studentAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_list_item_1,
      students
    )
    listView.adapter = studentAdapter

    // Register Context Menu for ListView
    registerForContextMenu(listView)

    // Handle ListView item click to set the selected position
    listView.onItemClickListener =
      AdapterView.OnItemClickListener { _, _, position, _ ->
        selectedStudentPosition = position
      }
  }

  // Inflate OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  // Handle OptionMenu item clicks
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.menu_add_new) {
      val intent = Intent(this, AddEditStudentActivity::class.java)
      startActivityForResult(intent, REQUEST_ADD_STUDENT)
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    val inflater = menuInflater
    inflater.inflate(R.menu.context_menu, menu) // Use your context menu XML here
  }

  // Handle Context Menu item clicks
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    when (item.itemId) {
      R.id.menu_edit -> {
        val student = students[info.position]
        val intent = Intent(this, AddEditStudentActivity::class.java).apply {
          putExtra(EXTRA_STUDENT_NAME, student.studentName)
          putExtra(EXTRA_STUDENT_ID, student.studentId)
          putExtra(EXTRA_POSITION, info.position)
        }
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
        return true
      }
      R.id.menu_remove -> {
        AlertDialog.Builder(this)
          .setTitle("Delete Student")
          .setMessage("Are you sure you want to delete ${students[info.position].studentName}?")
          .setPositiveButton("Yes") { _, _ ->
            students.removeAt(info.position)
            studentAdapter.notifyDataSetChanged()
          }
          .setNegativeButton("No", null)
          .show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      val name = data?.getStringExtra(EXTRA_STUDENT_NAME) ?: return
      val id = data.getStringExtra(EXTRA_STUDENT_ID) ?: return

      when (requestCode) {
        REQUEST_ADD_STUDENT -> {
          students.add(StudentModel(name, id))
          studentAdapter.notifyDataSetChanged()
        }
        REQUEST_EDIT_STUDENT -> {
          val position = data.getIntExtra(EXTRA_POSITION, -1)
          if (position >= 0) {
            students[position].studentName = name
            students[position].studentId = id
            studentAdapter.notifyDataSetChanged()
          }
        }
      }
    }
  }

  companion object {
    const val REQUEST_ADD_STUDENT = 1
    const val REQUEST_EDIT_STUDENT = 2
    const val EXTRA_STUDENT_NAME = "EXTRA_STUDENT_NAME"
    const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    const val EXTRA_POSITION = "EXTRA_POSITION"
  }
}