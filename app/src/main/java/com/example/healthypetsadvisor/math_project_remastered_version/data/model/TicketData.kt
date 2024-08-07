package com.example.healthypetsadvisor.math_project_remastered_version.data.model

data class TicketData(
    val hierarchicalTicketsList:List<TicketListItem>,
    val ticketTopics: List<String>,
    val mathTicketsNumbersAndPhotos: List<String>
)
