package com.vilinesoft.document_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vilinesoft.domain.util.toDoubleString
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.theme.LargeShape

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

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = state.count.toDoubleString()
            )

            Button(
                text = "OK",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                onIntent(DocumentEditContract.UpdateItemDialogIntent.DialogConfirmClick)
            }
        }
    }
}