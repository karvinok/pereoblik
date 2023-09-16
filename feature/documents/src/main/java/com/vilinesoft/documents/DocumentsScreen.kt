package com.vilinesoft.documents

import DialogAlertDelete
import DialogNewDocument
import DocumentItem
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.documents.DocumentsContract.*
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.mockedDocuments
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TopBarTitle
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.Icon
import com.vilinesoft.ui.theme.PereoblikTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentsScreen(
    modifier: Modifier = Modifier,
    onNavigateDocument: (id: String) -> Unit,
    viewModel: DocumentsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest {
            when(it) {
                is UIEffect.ShowToast -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
                is UIEffect.NavigateDocument -> onNavigateDocument(it.documentId)
            }
        }
    }
    DocumentsContent(
        modifier = modifier,
        state = uiState,
        intentBlock = viewModel::handleIntent
    )
}

@Composable
fun DocumentsContent(
    modifier: Modifier = Modifier,
    intentBlock: (UIIntent) -> Unit,
    state: UIState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        TopBarTitle(title = "Документи")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Button(
                text = if (state.isActionMode) "" else "Новий",
                icon = if (state.isActionMode) Icon.close else Icon.add,
                backgroundColor = if (state.isActionMode)
                    MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f),
                onClick = { intentBlock(UIIntent.AddClick) }
            )
            Button(
                text = "Відправити",
                icon = Icon.send,
                backgroundColor = if (state.isActionMode)
                    MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                modifier = Modifier.weight(1f),
                onClick = { intentBlock(UIIntent.SendClick) }
            )
            Button(
                text = "Видалити",
                icon = Icon.delete,
                backgroundColor = if (state.isActionMode)
                    MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                modifier = Modifier.weight(1f),
                onClick = { intentBlock(UIIntent.AskDeleteClick) }
            )
        }
        DocumentsList(
            documents = state.documents,
            modifier = Modifier.fillMaxHeight(),
            onItemClicked = { intentBlock(UIIntent.ItemClick(it)) },
            onItemLongClicked = { intentBlock(UIIntent.ItemLongClick(it)) }
        )
    }
    state.dialogCreateDocState?.let {
        DialogNewDocument(
            state = it,
            onDocumentTypeSelected = { type ->
                intentBlock(UIIntent.DocumentTypeSelect(type))
            },
            onDialogDismiss = { intentBlock(UIIntent.DialogDismiss) },
            onCommentChanged = { comment ->
                intentBlock(UIIntent.CommentChange(comment))
            },
            onDocumentCreateClicked = { intentBlock(UIIntent.DocumentCreateClick) }
        )
    }
    if (state.isDeleteAlertDialogVisible) {
        DialogAlertDelete(
            onDialogDismiss = { intentBlock(UIIntent.AlertDismissClick) },
            onDeleteClick = { intentBlock(UIIntent.AlertDeleteClick) },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DocumentsList(
    documents: ImmutableList<Document>,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit,
    onItemLongClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = documents,
        ) { index, document ->
            DocumentItem(
                document = document,
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

@Preview
@Composable
fun PreviewDocumentsScreen() {
    PereoblikTheme {
        DocumentsContent(
            state = UIState(
                isActionMode = false,
                documents = mockedDocuments(),
                //dialogState = CreateDocumentDialogState(),
                isDeleteAlertDialogVisible = false
            ),
            intentBlock = {}
        )
    }
}

