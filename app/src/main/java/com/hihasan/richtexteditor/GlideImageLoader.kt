package com.hihasan.richtexteditor

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzy.imagepicker.loader.ImageLoader
import java.io.File

class GlideImageLoader : ImageLoader {
    override fun displayImage(
        activity: Activity?,
        path: String?,
        imageView: ImageView?,
        width: Int,
        height: Int
    ) {
        Glide.with(activity!!).load(Uri.fromFile(File(path))).into(imageView!!)
    }


    override fun clearMemoryCache() {}
}