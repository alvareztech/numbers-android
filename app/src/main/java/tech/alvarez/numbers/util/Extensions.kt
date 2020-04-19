package tech.alvarez.numbers.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url)
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun Context.show(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}