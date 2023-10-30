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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.document_edit.DocumentEditContract.*
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.model.mockedDocuments
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.ui.components.TopBarTitle
import com.vilinesoft.ui.components.keyEventHandler
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.FontSize
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.document?.items.isNullOrEmpty()) {
                    Text(
                        text = "Товарів немає, \n спробуйте засканувати один",
                        textAlign = TextAlign.Center
                    )
                } else {
                    ItemsList(
                        documents = state.document?.items!!,
                        modifier = Modifier.align(Alignment.TopCenter),
                        onItemClicked = { onIntent(UIIntent.ItemClick(it)) },
                        onItemLongClicked = { onIntent(UIIntent.ItemLongClick(it)) }
                    )
                }
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
    state.dialogUpdateItemState?.let {
        ItemUpdateDialog(
            state = it,
            onIntent = onIntent,
        )
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
                dialogItemCreationVisible = false,
                dialogUpdateItemState = UpdateItemDialogState(
                    id = "",
                    itemName = "Name",
                    barcode = "",
                    isNameEditable = false,
                    qtyFact = 1.0,
                    qtyBalance = 1.0,
                    count = 1.0,
                    price = 1.0,
                    unitType = "шт",
                    isMode3 = false
                )
            ),
            modifier = Modifier,
            onIntent = {}
        )
    }
}