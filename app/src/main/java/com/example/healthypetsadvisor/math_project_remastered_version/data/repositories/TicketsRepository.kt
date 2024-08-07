package com.example.healthypetsadvisor.math_project_remastered_version.data.repositories

import android.content.Context
import com.example.healthypetsadvisor.math_project_remastered_version.R
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketData
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketListItem
import javax.inject.Inject

class TicketsRepository @Inject constructor(private val appContext: Context) {
    fun getTickets(): TicketData {
        val mathTickets = appContext.resources.getStringArray(R.array.matan_tikets)
        val mathTicketsNumbersAndPhotos =
            appContext.resources.getStringArray(R.array.matan_tikets_numbers_and_photos)

        return TicketData(
            getHierarchicalTicketsList(mathTickets),
            getTicketTopics(mathTickets),
            mathTicketsNumbersAndPhotos.toList()
        )
    }

    //Отправить на бекенд, пусть список билетов от туда приходит,
    //а в каждом Билете ещё должен быть список url-ов изображений.

    private fun getHierarchicalTicketsList(mathTickets: Array<String>): List<TicketListItem> {
        val hierarchicalTicketsList = mutableListOf<TicketListItem>()
        var i = 0

        while (i < mathTickets.size && mathTickets[i].indexOf('|') == 2) {
            val partTikets = TicketListItem(mathTickets[i])
            i++
            while (i < mathTickets.size && mathTickets[i].indexOf('|') == 5) {
                val tiket = TicketListItem(mathTickets[i])
                i++
                while (i < mathTickets.size && mathTickets[i].indexOf('|') == 8) {
                    tiket.childs.add(TicketListItem(mathTickets[i]))
                    i++
                }
                partTikets.childs.add(tiket)
            }
            hierarchicalTicketsList.add(partTikets)
        }
        return hierarchicalTicketsList
    }

    private fun getTicketTopics(mathTickets: Array<String>): List<String> {
        val mathTicketPoints = mutableListOf<String>()

        for (line in mathTickets) {
            if (line.indexOf('|') == 8) {
                mathTicketPoints.add(line)
            }
        }

        return mathTicketPoints
    }
}
