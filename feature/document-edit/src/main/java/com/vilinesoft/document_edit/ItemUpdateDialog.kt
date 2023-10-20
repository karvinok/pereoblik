package com.vilinesoft.document_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.LargeShape
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
internal fun ItemUpdateDialog(
    state: DocumentEditContract.UpdateItemDialogState,
    onIntent: (DocumentEditContract.UIIntent) -> Unit
) {
    Dialog(onDismissRequest = { onIntent(DocumentEditContract.UpdateItemDialogIntent.DialogDismiss) }) {
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
            TextField(
                isEnabled = state.isNameEditable,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = state.itemName,
                onValueChange = {
                    onIntent(
                        DocumentEditContract.UpdateItemDialogIntent.NameChanged(
                            it.text
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier,
                    fontSize = FontSize.extraLarge,
                    text = state.count.toDoubleString()
                )
                Text(
                    modifier = Modifier,
                    text = " із "
                )
                Text(
                    modifier = Modifier,
                    fontSize = FontSize.medium,
                    text = state.count.toDoubleString()
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.defaultMinSize(minWidth = 50.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.medium,
                        text = "Ціна"
                    )
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.extraLarge,
                        text = state.price.toDoubleString()
                    )
                }
                Text(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                    textAlign = TextAlign.Center,
                    fontSize = FontSize.extraLarge,
                    text = state.count.toDoubleString()
                )
                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 50.dp),
                    fontSize = FontSize.extraLarge,
                    textAlign = TextAlign.Center,
                    text = state.unitType?: ""
                )
            }
            Button(
                text = "OK",
                backgroundColor = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                onIntent(DocumentEditContract.UpdateItemDialogIntent.DialogConfirmClick)
            }
        }
    }
}


@Preview
@Composable
fun ItemUpdateDialogPreview() {
    PereoblikTheme {
        ItemUpdateDialog(
            state = DocumentEditContract.UpdateItemDialogState(
                "",
                false,
                9.0,
                12.0,
                3.0,
                44.50,
                "шт",
            ),
            onIntent = {}
        )
    }
}
