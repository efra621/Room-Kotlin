package app.gratumain.roomandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.gratumain.roomandroid.data.model.Notes
import app.gratumain.roomandroid.R
import kotlinx.android.synthetic.main.item_notes.view.*

class AdapterNotes : RecyclerView.Adapter<AdapterNotes.ViewHolder>() {

    private var notes = emptyList<Notes>()
    var OnItemClick : ((Notes) -> Unit)? = null

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = notes[position]
        holder.itemView.tvTitle.text = currentItem.noteTitle
        holder.itemView.tvDescription.text = currentItem.noteDescription

        holder.itemView.setOnClickListener{
            OnItemClick?.invoke(notes[position])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(notes: List<Notes>){
        this.notes = notes
        notifyDataSetChanged()
    }
}