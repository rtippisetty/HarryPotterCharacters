package dev.ranga.hpcharacters.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.ranga.hpcharacters.R

@Composable
fun ImageView(
    imageUrl: String,
    name: String,
    modifier: Modifier = Modifier
) {

    val painter = if (LocalInspectionMode.current) {
        painterResource(id = R.drawable.place_image)
    } else {
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED) // Enable caching for better performance
                .networkCachePolicy(CachePolicy.READ_ONLY)
                .placeholder(R.drawable.place_image) // Placeholder before image loads
                .error(R.drawable.place_image) // Fallback image if loading fails
                .build(),
        )
    }
    Image(
        painter = painter,
        contentDescription = name,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}