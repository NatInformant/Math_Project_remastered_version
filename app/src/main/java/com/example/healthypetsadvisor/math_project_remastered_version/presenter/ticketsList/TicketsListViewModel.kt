package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import androidx.lifecycle.ViewModel
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketData
import com.example.healthypetsadvisor.math_project_remastered_version.domain.GetTicketsUseCase
import javax.inject.Inject

class TicketsListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {
    var ticketsData: TicketData? = null
    init{
        getTicketsData()
    }
    private fun getTicketsData() {
        ticketsData = getTicketsUseCase()
    }
}
