package com.vilinesoft.document_edit

import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.document_edit.DocumentEditContract.*
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.model.mockedDocuments
import com.vilinesoft.domain.util.takeIfDouble
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.components.TopBarTitle
import com.vilinesoft.ui.components.keyEventHandler
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.LargeShape
import com.vilinesoft.ui.theme.PereoblikTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentEditScreen(
    modifier: Modifier = Modifier,
    onCloseRequest: () -> Unit,
    viewModel: DocumentEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest {
            when (it) {
                is UIEffect.CloseScreen -> onCloseRequest()
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.subscribeKeyEvents()
        onDispose(viewModel::unsubscribeKeyEvents)
    }

    DocumentEditContent(
        modifier = modifier.keyEventHandler(viewModel),
        state = uiState,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun DocumentEditContent(
    modifier: Modifier = Modifier,
    onIntent: (UIIntent) -> Unit,
    state: UIState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBarTitle(title = "Док. №${state.document?.numberDoc}")
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.weight(1f)
        ) {
            state.document?.items?.let {
                ItemsList(
                    documents = it,
                    onItemClicked = { onIntent(UIIntent.ClickItem) },
                    onItemLongClicked = { onIntent(UIIntent.ClickItem) }
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = state.barcode.isNotBlank(),
                modifier = Modifier.align(Alignment.BottomCenter),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = DefaultShape
                        )
                        .padding(vertical = 8.dp),
                    text = state.barcode,
                    fontSize = FontSize.extraLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (state.dialogItemCreationVisible) {
        ItemCreationDialog(
            onConfirmClick = { onIntent(it) },
            onDismissRequest = { onIntent(it) }
        )
    }
}

@Composable
fun ItemCreationDialog(
    onConfirmClick: (UIIntent.CreateItemConfirmClick) -> Unit,
    onDismissRequest: (UIIntent.DismissCreationDialog) -> Unit
) {
    val nameState = rememberSaveable { mutableStateOf("") }
    val priceState = rememberSaveable { mutableStateOf("") }
    val qtyState = rememberSaveable { mutableStateOf("1") }

    Dialog(onDismissRequest = { onDismissRequest(UIIntent.DismissCreationDialog) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = LargeShape
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Новий товар")
            TextField(
                titleString = "Назва товару",
                value = nameState.value,
                onValueChange = { nameState.value = it.text }
            )
            TextField(
                titleString = "Ціна",
                value = priceState.value,
                keyboardType = KeyboardType.Decimal,
                onValueChange = {
                    priceState.value = it.text.takeIfDouble()?: priceState.value
                }
            )
            TextField(
                titleString = "Кількість",
                value = qtyState.value,
                keyboardType = KeyboardType.Decimal,
                onValueChange = {
                    qtyState.value = it.text.takeIfDouble()?: qtyState.value
                }
            )
            Button(
                text = "Створити",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                onConfirmClick(UIIntent.CreateItemConfirmClick(
                    name = nameState.value,
                    price = priceState.value,
                    qty = qtyState.value
                ))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsList(
    documents: ImmutableList<DocumentItem>,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit,
    onItemLongClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = documents,
        ) { index, item ->
            Item(
                item = item,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .combinedClickable(
                        onClick = {
                            onItemClicked.invoke(index)
                        },
                        onLongClick = {
                            onItemLongClicked.invoke(index)
                        },
                    )
            )
        }
    }
}

@Composable
fun Item(
    item: DocumentItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = DefaultShape
            )
    ) {
        Text(
            text = item.name.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = item.qtyFact.toDoubleString(),
                fontSize = FontSize.extraLarge,
                modifier = Modifier
            )
            Text(
                text = item.qtyBalance.toDoubleString(),
                fontSize = FontSize.extraLarge,
                modifier = Modifier
            )
        }
    }
}


@Preview
@Composable
fun DocumentEditPreview() {
    PereoblikTheme {
        DocumentEditContent(
            state = UIState(
                document = mockedDocuments()[0],
                barcode = "123",
                dialogItemCreationVisible = true
            ),
            modifier = Modifier,
            onIntent = {}
        )
    }
}