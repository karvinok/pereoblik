import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.theme.Icon
import com.vilinesoft.ui.theme.LargeShape
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
fun DialogAlertDelete(
    onDialogDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
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
            Text(text = "Видалити обрані документи?")

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    modifier = Modifier.weight(1f),
                    text = "Ні",
                    onClick = onDialogDismiss,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.weight(2f),
                    icon = Icon.delete,
                    backgroundColor = MaterialTheme.colorScheme.error,
                    text = "Видалити",
                    onClick = onDeleteClick,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlertDialog() {
    PereoblikTheme {
        DialogAlertDelete(onDialogDismiss = {}) {}
    }
}