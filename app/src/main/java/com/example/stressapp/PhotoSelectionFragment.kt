package com.example.stressapp

import android.content.Intent
import android.media.AsyncPlayer
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.lifecycle.Observer
import java.time.Instant

class PhotoSelectionFragment : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var moreImagesBtn: Button

    private lateinit var viewModel: PhotoSelectionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_selection, container, false)

        gridView = view.findViewById(R.id.gvImageSelection)
        moreImagesBtn = view.findViewById(R.id.btnMoreImages)

        viewModel = ViewModelProvider(this).get(PhotoSelectionViewModel::class.java)
        viewModel.currentPage.observe(viewLifecycleOwner, Observer {
            loadImagesForCurrentPage()
        })

        moreImagesBtn.setOnClickListener() {
            viewModel.switchPage()
            stopServices()
        }

        startServices()


        return view
    }
    override fun onResume() {
        super.onResume()
        loadImagesForCurrentPage()
    }

    private fun loadImagesForCurrentPage() {
        Log.i("Test", "Working in the loadImages Before")
        val arrayResourceId = viewModel.getPageImagesResourceArray()
        val typedArray = requireContext().resources.obtainTypedArray(arrayResourceId)
        val imageResources = mutableListOf<Int>()

        for (i in 0 until typedArray.length()) {
            imageResources.add(typedArray.getResourceId(i, -1))
        }

        typedArray.recycle()
        Log.i("Test", "Working in the loadImages After")

        // Set the adapter for the GridView using the imageResources
        gridView.adapter = ImageAdapter(requireContext(), imageResources)

        // Set the item click listener for GridView items
        gridView.setOnItemClickListener { _, _, position, _ ->
            stopServices()
            val selectedImageResource = imageResources[position]
            Log.i("myTag", selectedImageResource.toString())
            Log.i("myTag", position.toString())
            val time = System.currentTimeMillis()

            val intent = Intent(requireContext(), PhotoConfirmationActivity::class.java).apply {
                putExtra(PhotoConfirmationActivity.IMAGE_RESOURCE_KEY, selectedImageResource)
                putExtra(PhotoConfirmationActivity.POSITION_KEY, position)
                putExtra(PhotoConfirmationActivity.TIMESTAMP_KEY, time)
            }
            startActivity(intent)
        }
    }

    private fun startServices() {
        Intent(requireContext(), SoundService::class.java).also {
            requireContext().startService(it)
        }

        Intent(requireContext(), VibrationService::class.java).also {
            requireContext().startService(it)
        }
    }

    private fun stopServices() {
        Intent(requireContext(), SoundService::class.java).also {
            requireContext().stopService(it)
        }

        Intent(requireContext(), VibrationService::class.java).also {
            requireContext().stopService(it)
        }
    }

}