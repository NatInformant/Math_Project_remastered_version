package com.example.healthypetsadvisor.math_project_remastered_version.utils

object TicketListAdapterUtils {
    fun getLinksToCurrentTicketPhotos(
        sheetPartNumber: Int,
        sheetTicketNumber: Int,
        ticketToPhotos: List<String>
    ): List<String> {

        var partNumber = 0
        var ticketNumber = 0
        var ticketFounded = false
        val linksToTicketPhotos = ArrayList<String>()

        for (line in ticketToPhotos) {
            if (line.contentEquals("Билет 1")) {
                ticketNumber = 1
                partNumber++
            } else if (line.contains("Билет")) {
                ticketNumber++
            }
            if (line.contains("Билет") && ticketFounded) {
                break
            }

            if (ticketFounded) {
                linksToTicketPhotos.add(line)
            }

            if (partNumber == sheetPartNumber && ticketNumber == sheetTicketNumber) {
                ticketFounded = true
            }
        }

        return linksToTicketPhotos
    }
}
