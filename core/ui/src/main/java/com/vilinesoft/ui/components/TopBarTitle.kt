package com.vilinesoft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.theme.DefaultShape

@Composable
fun TopBarTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.inverseSurface, shape = DefaultShape)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
}