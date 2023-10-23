package com.example.stressapp

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView

class ImageAdapter(private val context: Context, private val images: List<ImageResource>) : BaseAdapter() {

    private val displayMetrics = context.resources.displayMetrics
    private val gridViewWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 372f, displayMetrics).toInt()

    private val imageSize = gridViewWidth / 4
    override fun getCount(): Int = images.size

    override fun getItem(position: Int): ImageResource = images[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(imageSize, imageSize)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(images[position])
        return imageView
    }
}