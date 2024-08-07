package com.example.healthypetsadvisor.math_project_remastered_version.data.model

import com.example.healthypetsadvisor.math_project_remastered_version.R

data class TicketListItem(
    val title: String,
    val childs: MutableList<TicketListItem> = mutableListOf(),
) {
    val iconResource: Int
        get() {
            if (childs.size == 0) return R.drawable.paper
            if (childs[0].childs.size == 0) return R.drawable.book
            return R.drawable.bookshelf
        }
}
