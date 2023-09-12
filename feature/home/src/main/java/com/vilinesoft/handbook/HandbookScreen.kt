package com.vilinesoft.handbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.handbook.HandbookContract.*
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.keyEventHandler
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.LargeShape
import com.vilinesoft.ui.theme.PereoblikTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun HandbookScreen(
    modifier: Modifier = Modifier,
    onCloseRequest: () -> Unit,
    viewModel: HandbookViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest {
            when(it) {
                is UIEffect.CloseScreen -> onCloseRequest()
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.subscribeKeyEvents()
        onDispose(viewModel::unsubscribeKeyEvents)
    }
    HandbookContent(
        state = uiState,
        modifier = modifier.keyEventHandler(viewModel),
        onDismissRequest = onCloseRequest,
    )
}

@Composable
fun HandbookContent(
    state: UIState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spaceHeight = 8.dp
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = LargeShape
            ),
        verticalArrangement = Arrangement.spacedBy(spaceHeight)
    ) {
        Spacer(modifier = Modifier.height(spaceHeight))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.good?.goodName?: "Скануйте або введіть код",
            fontSize = FontSize.extraLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(spaceHeight))
        Row {
            state.good?.goodPrice?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it.toDoubleString(),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
            state.good?.goodCount?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it.toString(),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        val imageBgColor = if (state.notFound)
            MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.background

        Box(modifier = Modifier
            .defaultMinSize(minHeight = 100.dp)
            .fillMaxWidth()
        ) {
            when {
                state.barcode.isBlank() && !state.loading -> {
                    Image(
                        modifier = Modifier
                            .height(100.dp)
                            .background(imageBgColor)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        imageVector = Icons.Default.Menu,
                        contentDescription = ""
                    )
                }
                state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.Center)
                    )
                }
                state.barcode.isNotBlank() -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = state.barcode,
                        fontSize = FontSize.extraLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Button(
            text = "Назад",
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(50.dp),
            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            shape = LargeShape.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp)),
            onClick = onDismissRequest
        )
    }
}

@Preview
@Composable
fun HandbookPreview() {
    PereoblikTheme {
        HandbookContent(
            state = UIState(),
            modifier = Modifier,
            onDismissRequest = {}
        )
    }
}