package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.TicketListElementBinding

class TicketListAdapter(
    private val showCurrentTicket: () -> Unit
) : ListAdapter<TicketAndLevel, TicketListAdapter.TicketElementViewHolder>(FoodBrandDiffUtil()) {
    var ticketsToPhotos: List<String> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketElementViewHolder {
        val elementBinding =
            TicketListElementBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

        return TicketElementViewHolder(
            elementBinding,
            showCurrentTicket,
            ::currentElementClickListener,
        )
    }

    override fun onBindViewHolder(holder: TicketElementViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    private fun currentElementClickListener(ticketAndLevel: TicketAndLevel) {
        val currentElementIndex = currentList.indexOf(ticketAndLevel)
        if (currentElementIndex == currentList.size - 1 ||
            currentList[currentElementIndex + 1].level <= ticketAndLevel.level
        ) {
            showCurrentElementChildren(ticketAndLevel, currentElementIndex)
        } else {
            hideCurrentElementChildren(ticketAndLevel, currentElementIndex)
        }
    }

    private fun showCurrentElementChildren(
        ticketAndLevel: TicketAndLevel,
        currentElementIndex: Int
    ) {
        val newList = currentList.toMutableList()

        newList.addAll(
            currentElementIndex + 1,
            ticketAndLevel.ticket.childs.map {
                TicketAndLevel(
                    ticket = it,
                    level = ticketAndLevel.level + 1,
                )
            })
        submitList(newList)
    }

    private fun hideCurrentElementChildren(
        ticketAndLevel: TicketAndLevel,
        currentElementIndex: Int
    ) {
        val newList = currentList.toMutableList()

        newList.removeAll(
            currentList.subList(
                currentElementIndex + 1,
                getNextSameOrLowerLevelElementIndex(ticketAndLevel)
            )
        )

        submitList(newList)
    }

    private fun getNextSameOrLowerLevelElementIndex(ticketAndLevel: TicketAndLevel): Int {
        var nextSameOrLowerLevelElementIndex = -1

        for (x in (currentList.indexOf(ticketAndLevel) + 1)..<currentList.size) {
            if (currentList[x].level <= ticketAndLevel.level) {
                nextSameOrLowerLevelElementIndex = x
                break
            }
        }

        if (nextSameOrLowerLevelElementIndex == -1) {
            nextSameOrLowerLevelElementIndex = currentList.size
        }

        return nextSameOrLowerLevelElementIndex
    }

    class TicketElementViewHolder(
        private val binding: TicketListElementBinding,
        private val showCurrentTicket: () -> Unit,
        private val currentElementClickListener: (TicketAndLevel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(element: TicketAndLevel) = with(binding) {
            setUpCurrentElementTitle(element)

            setUpCurrentElementClickListener(element)
        }

        private fun TicketListElementBinding.setUpCurrentElementClickListener(
            element: TicketAndLevel
        ) {
            ticketListElement.setOnClickListener {
                if (element.ticket.childs.size == 0) {
                    showCurrentTicket()
                    return@setOnClickListener
                }

                currentElementClickListener(element)
            }
        }

        private fun TicketListElementBinding.setUpCurrentElementTitle(
            element: TicketAndLevel
        ) = with(title) {

            isAllCaps = false
            setTypeface(null, Typeface.NORMAL)

            text = element.ticket.title

            setCompoundDrawablesWithIntrinsicBounds(element.ticket.iconResource, 0, 0, 0)
            setPadding((element.level + 1) * 30, 0, 0, 0)

            if (element.level == 0) {
                isAllCaps = true
                setTypeface(null, Typeface.BOLD)
            } else if (element.level == 1) {
                setTypeface(null, Typeface.BOLD)
            }
        }
    }


    class FoodBrandDiffUtil : DiffUtil.ItemCallback<TicketAndLevel>() {
        override fun areItemsTheSame(
            oldItem: TicketAndLevel,
            newItem: TicketAndLevel
        ): Boolean {
            return oldItem.ticket.title == newItem.ticket.title
        }

        override fun areContentsTheSame(
            oldItem: TicketAndLevel,
            newItem: TicketAndLevel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
