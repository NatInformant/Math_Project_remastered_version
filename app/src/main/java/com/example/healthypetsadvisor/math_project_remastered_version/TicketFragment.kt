package com.example.healthypetsadvisor.math_project_remastered_version

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.CreationExtras
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.FragmentTicketBinding

class TicketFragment : Fragment(R.layout.fragment_ticket) {

    private val binding: FragmentTicketBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
