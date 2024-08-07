package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthypetsadvisor.math_project_remastered_version.App.Companion.viewModelFactory
import com.example.healthypetsadvisor.math_project_remastered_version.R
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.FragmentTicketsListBinding

class TicketsListFragment : Fragment(R.layout.fragment_tickets_list) {

    private val binding: FragmentTicketsListBinding by viewBinding()
    private val viewModel: TicketsListViewModel by viewModels { viewModelFactory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val a = viewModel.getTickets()
    }
}
