package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticket

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.TicketSheetListElementBinding

class TicketSheetsListAdapter :
    ListAdapter<Bitmap, TicketSheetsListAdapter.TicketSheetElementViewHolder>(TicketSheetDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketSheetElementViewHolder {
        val elementBinding =
            TicketSheetListElementBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

        return TicketSheetElementViewHolder(
            elementBinding
        )
    }

    override fun onBindViewHolder(holder: TicketSheetElementViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class TicketSheetElementViewHolder(
        private val binding: TicketSheetListElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(element: Bitmap) = with(binding) {
            photo.setImageBitmap(element)
        }
    }


    class TicketSheetDiffUtil : DiffUtil.ItemCallback<Bitmap>() {
        override fun areItemsTheSame(
            oldItem: Bitmap,
            newItem: Bitmap
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Bitmap,
            newItem: Bitmap
        ): Boolean {
            return oldItem.sameAs(newItem)
        }
    }


}
