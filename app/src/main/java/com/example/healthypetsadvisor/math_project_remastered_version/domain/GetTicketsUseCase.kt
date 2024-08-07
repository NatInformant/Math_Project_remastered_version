package com.example.healthypetsadvisor.math_project_remastered_version.domain

import com.example.healthypetsadvisor.math_project_remastered_version.data.repositories.TicketsRepository
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: TicketsRepository
) {
    operator fun invoke() = ticketsRepository.getTickets()
}
