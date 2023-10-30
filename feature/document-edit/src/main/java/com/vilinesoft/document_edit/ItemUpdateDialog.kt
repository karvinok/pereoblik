package com.vilinesoft.document_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent.DialogConfirmClick
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent.DialogDismiss
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent.NameChanged
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.components.clickableNoIndication
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.LargeShape
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
internal fun ItemUpdateDialog(
    state: DocumentEditContract.UpdateItemDialogState,
    onIntent: (UpdateItemDialogIntent) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickableNoIndication {
                onIntent(DialogDismiss)
            }
            .background(
                color = Color.Black.copy(0.5f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickableNoIndication()
                .padding(16.dp)
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
                    onIntent(NameChanged(it.text))
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
                    text = state.qtyFact.toDoubleString()
                )
                Text(
                    modifier = Modifier,
                    text = " із "
                )
                Text(
                    modifier = Modifier,
                    fontSize = FontSize.medium,
                    text = state.qtyBalance.toDoubleString()
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
                            color = if (state.isMode3) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                    textAlign = TextAlign.Center,
                    fontSize = FontSize.giant,
                    text = state.count.toDoubleString()
                )
                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 50.dp),
                    fontSize = FontSize.extraLarge,
                    textAlign = TextAlign.Center,
                    text = state.unitType ?: ""
                )
            }
            Button(
                text = "OK",
                backgroundColor = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                onClick = { onIntent(DialogConfirmClick) }
            )
        }
    }
}


@Preview
@Composable
fun ItemUpdateDialogPreview() {
    PereoblikTheme {
        ItemUpdateDialog(
            state = DocumentEditContract.UpdateItemDialogState(
                id = "",
                itemName = "",
                isNameEditable = false,
                qtyFact = 9.0,
                qtyBalance = 12.0,
                count = 323.0,
                price = 44.50,
                barcode = "",
                unitType = "шт",
                isMode3 = false
            ),
            onIntent = {}
        )
    }
}
