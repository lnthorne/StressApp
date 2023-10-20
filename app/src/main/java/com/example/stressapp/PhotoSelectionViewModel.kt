package com.example.stressapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

typealias ImageResource = Int
class PhotoSelectionViewModel(private val context: Context) : ViewModel() {
    private val _currentPage = MutableLiveData<Int>(1)
    val currentPage: LiveData<Int> get() = _currentPage

    fun switchPage() {
        _currentPage.value = (_currentPage.value ?: 1) % 3 + 1
    }

    fun getImagesForPage(): List<ImageResource> {
        val arrayResourceId = when(_currentPage.value) {
            1 -> R.array.page_1_images
            2 -> R.array.page_2_images
            3 -> R.array.page_3_images
            else -> throw IllegalArgumentException("Invalid page number: ${_currentPage.value}")
        }

        val typedArray = context.resources.obtainTypedArray(arrayResourceId)
        val imageResources = mutableListOf<ImageResource>()

        for (i in 0 until typedArray.length()) {
            imageResources.add(typedArray.getResourceId(i, -1))
        }

        typedArray.recycle()

        return imageResources
    }

    fun selectImage(image: ImageResource ) {

    }
}