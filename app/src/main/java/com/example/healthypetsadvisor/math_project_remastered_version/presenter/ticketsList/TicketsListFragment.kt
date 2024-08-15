package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthypetsadvisor.math_project_remastered_version.App.Companion.viewModelFactory
import com.example.healthypetsadvisor.math_project_remastered_version.R
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketLinksToPhotos
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketListItem
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
        setUpFilter()
    }

    private fun setUpRecyclerView() = with(binding.ticketsList) {
        adapter = ticketsListAdapter
        layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    private fun setUpListToRecyclerView() {
        if (viewModel.savedTicketsList == null) {
            ticketsListAdapter.submitList(
                viewModel.ticketsData!!.hierarchicalTicketsList
                    .map {
                        TicketAndLevel(
                            ticket = it,
                            level = 0
                        )
                    }
            )
            ticketsListAdapter.originalList = viewModel.ticketsData!!.hierarchicalTicketsList
                .map {
                    TicketAndLevel(
                        ticket = it,
                        level = 0
                    )
                }
        } else {
            ticketsListAdapter.submitList(viewModel.savedTicketsList)
            ticketsListAdapter.originalList = viewModel.savedTicketsList!!
        }

        ticketsListAdapter.ticketsTopics = viewModel.ticketsData!!.ticketTopics.map { topic ->
            TicketAndLevel(ticket = TicketListItem(topic), level = 0)
        }
        ticketsListAdapter.ticketsToPhotos = viewModel.ticketsData!!.mathTicketsNumbersAndPhotos
    }

    private fun setUpFilter() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                ticketsListAdapter.filter.filter(s.toString())
            }
        })
    }

    private fun showCurrentTicket(linksToTicketSheets: List<String>) {
        viewModel.savedTicketsList = ticketsListAdapter.currentList

        val action = TicketsListFragmentDirections.actionTicketsListFragmentToTicketFragment(
            TicketLinksToPhotos(
                linksToTicketSheets
            )
        )
        findNavController().navigate(action)
    }
}
