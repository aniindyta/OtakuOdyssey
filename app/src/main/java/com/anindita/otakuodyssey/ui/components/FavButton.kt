package com.anindita.otakuodyssey.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"

    IconButton(
        onClick = onClick,
        modifier = modifier,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    )
}

@Preview
@Composable
fun FavButtonPreview() {
    var isFavorite by remember { mutableStateOf(false) }

    Surface(
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    ) {
        FavButton(
            isFavorite = isFavorite,
            onClick = { isFavorite = !isFavorite }
        )
    }
}