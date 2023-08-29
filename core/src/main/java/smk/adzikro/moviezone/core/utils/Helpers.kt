package smk.adzikro.moviezone.core.utils

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

fun debug(msg : String){
    Timber.e(msg)
}

val options = RequestOptions()
    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    .transform(CenterCrop(), RoundedCorners(10))