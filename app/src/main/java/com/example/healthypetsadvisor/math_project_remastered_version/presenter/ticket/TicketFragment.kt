package com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthypetsadvisor.math_project_remastered_version.R
import com.example.healthypetsadvisor.math_project_remastered_version.data.model.TicketLinksToPhotos
import com.example.healthypetsadvisor.math_project_remastered_version.databinding.FragmentTicketBinding
import java.io.IOException
import java.io.InputStream

class TicketFragment : Fragment(R.layout.fragment_ticket) {

    private val binding: FragmentTicketBinding by viewBinding()
    private val adapter: TicketSheetsListAdapter = TicketSheetsListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linksToPhotos =
            (arguments?.getSerializable("linksToTicketPhotos")
                    as TicketLinksToPhotos).linksToTicketPhotos

        val photos = ArrayList<Bitmap?>()
        for (link in linksToPhotos) {
            photos.add(getBitmapFromAsset(link))
        }

        binding.ticketSheetsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ticketSheetsList.adapter = adapter
        adapter.submitList(photos)
    }

    private fun getBitmapFromAsset(filePath: String?): Bitmap? {
        val inputStream: InputStream
        var bitmap: Bitmap? = null
        try {
            inputStream = requireContext().assets.open(filePath!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return bitmap
    }

}
