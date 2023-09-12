package com.vilinesoft.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.theme.PereoblikTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onHandbookClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        modifier = modifier,
        onHandbookClick = onHandbookClick,
        onDocumentsClick = onDocumentsClick,
        onSettingsClick = onSettingsClick,
        state = uiState
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onHandbookClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    state: HomeContract.UIState
) {
    Box(modifier = modifier.background(
        color = MaterialTheme.colorScheme.background
    )) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            val itemModifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)

            Button(
                text = "Довідник",
                modifier = itemModifier,
                onClick = onHandbookClick
            )
            Button(
                text = "Документи",
                modifier = itemModifier,
                onClick = onDocumentsClick
            )
            Button(
                text = "Налаштування",
                modifier = itemModifier,
                onClick = onSettingsClick
            )
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    PereoblikTheme {
        HomeContent(
            onHandbookClick = {},
            onDocumentsClick = {},
            onSettingsClick = {},
            state = HomeContract.UIState(),
        )
    }
}