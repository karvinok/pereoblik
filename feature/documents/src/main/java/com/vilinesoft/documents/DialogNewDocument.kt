import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vilinesoft.documents.DocumentsContract
import com.vilinesoft.domain.model.DocumentType
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.components.SelectionField
import com.vilinesoft.ui.model.IntIdName
import com.vilinesoft.ui.theme.LargeShape

@Composable
fun DialogNewDocument(
    state: DocumentsContract.CreateDocumentDialogState,
    onDocumentTypeSelected: (DocumentType) -> Unit,
    onDialogDismiss: () -> Unit,
    onCommentChanged: (String) -> Unit,
    onDocumentCreateClicked: () -> Unit
) {
    Dialog(onDismissRequest = onDialogDismiss) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = LargeShape
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Новий документ")
            TextField(
                titleString = "Коментар",
                value = state.comment,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onCommentChanged(it.text) }
            )
            SelectionField(
                titleString = "Тип",
                selectedElementTitle = state.selectedDocType.toString(),
                list = state.documentTypes.map { IntIdName(it.typeNumber, it.toString()) },
                selectedBlock = { element ->
                    element?.let {
                        onDocumentTypeSelected(DocumentType.valueOf(it.id))
                    }
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Створити",
                onClick = onDocumentCreateClicked,
            )
        }
    }
}