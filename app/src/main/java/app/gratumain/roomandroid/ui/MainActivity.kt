package app.gratumain.roomandroid.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.gratumain.roomandroid.data.model.Notes
import app.gratumain.roomandroid.R
import app.gratumain.roomandroid.presentation.ViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class
MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AdapterNotes()
        rvNotes.adapter = adapter
        rvNotes.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.readAllNotes.observe(this, { notes ->
            adapter.setData(notes)
        })

        floatingActionButton.setOnClickListener {
            showAddNoteDialog()
        }

        adapter.OnItemClick = { notes ->
            showActionDialog(notes)
        }
    }

    private fun showActionDialog(notes: Notes) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Delete") { _, _ ->
            viewModel.deleteData(notes)
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Update") { _, _ ->
            showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Select Action")
        builder.create().show()
    }

    //Update
    private fun showUpdateDialog(notes: Notes) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_new_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteTitle = dialog.findViewById<TextInputEditText>(R.id.etNoteTitle)
        val etNoteDescription = dialog.findViewById<TextInputEditText>(R.id.etNoteDescription)

        etNoteTitle.setText(notes.noteTitle)
        etNoteDescription.setText(notes.noteDescription)

        dialog.findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            if (inputCheck(etNoteTitle.text.toString(), etNoteDescription.text.toString())) {
                val notes =
                    Notes(notes.id, etNoteTitle.text.toString(), etNoteDescription.text.toString())
                viewModel.update(notes)
                Toast.makeText(this, "Note update", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Ingrese un texto", Toast.LENGTH_LONG).show()
            }
        }

        dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    private fun showAddNoteDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteTitle = dialog.findViewById<TextInputEditText>(R.id.etNoteTitle)
        val etNoteDescription = dialog.findViewById<TextInputEditText>(R.id.etNoteDescription)

        dialog.findViewById<Button>(R.id.buttonAcept).setOnClickListener {
            if (inputCheck(etNoteTitle.text.toString(), etNoteDescription.text.toString())) {
                val notes =
                    Notes(id = 0, etNoteTitle.text.toString(), etNoteDescription.text.toString())
                viewModel.addData(notes)
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Ingrese un texto", Toast.LENGTH_LONG).show()
            }
        }

        dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.attributes = layoutParams

    }

    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean {
        return (!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteDescription))
    }
}