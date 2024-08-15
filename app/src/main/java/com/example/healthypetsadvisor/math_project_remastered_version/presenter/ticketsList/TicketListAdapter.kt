package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.BooleanObject
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.TicketListElementBinding
import com.example.healthypetsadvisor.math_project_remastered_version.utils.TicketListAdapterUtils.getLinksToCurrentTicketPhotos
import java.util.Locale

class TicketListAdapter(
    private val showCurrentTicket: (List<String>) -> Unit
) : ListAdapter<TicketAndLevel, TicketListAdapter.TicketElementViewHolder>(TicketDiffUtil()),
    Filterable {
    //Список строк в формате Билет.....Ссылки на фото билета.....Билет
    var ticketsToPhotos: List<String> = emptyList()
    //Список до применения поиска
    var originalList: List<TicketAndLevel> = emptyList()
    //Вопросы билетов
    var ticketsTopics: List<TicketAndLevel> = emptyList()
    var isSearchActive = BooleanObject(false)

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
            ticketsToPhotos,
            isSearchActive
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
        private val showCurrentTicket: (List<String>) -> Unit,
        private val currentElementClickListener: (TicketAndLevel) -> Unit,
        private val ticketsToPhotos: List<String>,
        private var isSearchActive: BooleanObject
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

                    val sheetPartNumber: Int = (element.ticket.title[0] - 48).code
                    val sheetTicketNumber: Int = element.ticket.title.substring(2, 4).toInt()

                    showCurrentTicket(
                        getLinksToCurrentTicketPhotos(
                            sheetPartNumber,
                            sheetTicketNumber,
                            ticketsToPhotos
                        )
                    )

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

            if (isSearchActive.value) {
                return
            }

            if (element.level == 0) {
                isAllCaps = true
                setTypeface(null, Typeface.BOLD)
            } else if (element.level == 1) {
                setTypeface(null, Typeface.BOLD)
            }
        }
    }


    class TicketDiffUtil : DiffUtil.ItemCallback<TicketAndLevel>() {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                constraint.toString().lowercase(Locale.getDefault())

                val result = FilterResults()
                if (constraint.toString().isNotEmpty()) {
                    isSearchActive.value = true

                    val filteredItems = mutableListOf<TicketAndLevel>()

                    filteredItems.addAll(ticketsTopics.filter {
                        it.ticket.title.lowercase(Locale.getDefault())
                            .startsWith(constraint.toString(), 9)
                    })

                    filteredItems.addAll(ticketsTopics.filter {
                        it.ticket.title.lowercase(Locale.getDefault()).contains(constraint)
                                && !filteredItems.contains(it)
                    })

                    result.count = filteredItems.size
                    result.values = filteredItems
                } else {
                    synchronized(this) {
                        isSearchActive.value = false

                        result.values = originalList
                        result.count = originalList.size
                    }
                }
                return result
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                submitList(results.values as List<TicketAndLevel>)
            }
        }
    }
}
