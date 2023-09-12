package com.vilinesoft.document_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.domain.model.mockedDocuments
import com.vilinesoft.ui.components.keyEventHandler
import com.vilinesoft.ui.theme.PereoblikTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentEditRoute(
    modifier: Modifier = Modifier,
    onCloseRequest: () -> Unit,
    viewModel: DocumentEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest {
            when(it) {
                is DocumentEditContract.UIEffect.CloseScreen -> onCloseRequest()
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.subscribeKeyEvents()
        onDispose(viewModel::unsubscribeKeyEvents)
    }
    DocumentEditScreen(
        modifier = modifier.keyEventHandler(viewModel),
        state = uiState
    )
}

@Composable
fun DocumentEditScreen(
    modifier: Modifier = Modifier,
    state: DocumentEditContract.UIState
) {
    Box(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Text(text = state.document?.id.toString())
    }
}

@Preview
@Composable
fun DocumentEditPreview() {
    PereoblikTheme {
        DocumentEditScreen(
            state = DocumentEditContract.UIState(mockedDocuments()[0]),
            modifier = Modifier,
        )
    }
}