package com.example.minesweeper_kotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper_kotlin.TableroRecyclerAdapter.MineTileViewHolder

class TableroRecyclerAdapter(private val cells: List<Cell>, private val lis: OnCellClickListener) :
    RecyclerView.Adapter<MineTileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    inner class MineTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var valueTextView: TextView
        fun bind(cell: Cell?) {
            itemView.setBackgroundColor(Color.rgb(233, 242, 241))
            itemView.setOnClickListener {
                val pos = adapterPosition
                val ret = lis.cellClick(pos)
                if (ret == Cell.bomba) {
                    valueTextView.setText(R.string.bomb)
                    itemView.setBackgroundColor(Color.rgb(200, 74, 74))
                } else {
                    valueTextView.text = Integer.toString(ret)
                    itemView.setBackgroundColor(Color.rgb(230, 173, 99))
                }
                itemView.isEnabled = false
            }
            itemView.setOnLongClickListener {
                val pos = adapterPosition
                lis.cellClikLong(pos)
                valueTextView.setText(R.string.flag)
                true
            }
        }

        init {
            valueTextView = itemView.findViewById(R.id.item_cell_value)
        }
    }
}