package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthypetsadvisor.math_project_remastered_version.App.Companion.viewModelFactory
import com.example.healthypetsadvisor.math_project_remastered_version.R
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.FragmentTicketsListBinding

class TicketsListFragment : Fragment(R.layout.fragment_tickets_list) {

    private val binding: FragmentTicketsListBinding by viewBinding()
    private val viewModel: TicketsListViewModel by viewModels { viewModelFactory }

    private val ticketsListAdapter = TicketListAdapter(
        showCurrentTicket = ::showCurrentTicket
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpListToRecyclerView()
    }

    private fun setUpRecyclerView() = with(binding.ticketsList) {
        adapter = ticketsListAdapter
        layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    private fun setUpListToRecyclerView() {
        ticketsListAdapter.submitList(
            viewModel.getTickets().hierarchicalTicketsList
                .map {
                    TicketAndLevel(
                        ticket = it,
                        level = 0
                    )
                }
        )
    }

    fun showCurrentTicket() {

    }
}
