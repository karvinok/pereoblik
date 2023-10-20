package com.vilinesoft.document_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vilinesoft.domain.util.takeIfDouble
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.theme.LargeShape


@Composable
internal fun ItemCreationDialog(
    onConfirmClick: (DocumentEditContract.UIIntent.CreateItemConfirmClick) -> Unit,
    onDismissRequest: (DocumentEditContract.UIIntent.DialogCreationDismiss) -> Unit
) {
    val nameState = rememberSaveable { mutableStateOf("") }
    val priceState = rememberSaveable { mutableStateOf("") }
    val qtyState = rememberSaveable { mutableStateOf("1") }

    Dialog(onDismissRequest = { onDismissRequest(DocumentEditContract.UIIntent.DialogCreationDismiss) }) {
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
                    priceState.value = it.text.takeIfDouble() ?: priceState.value
                }
            )
            TextField(
                titleString = "Кількість",
                value = qtyState.value,
                keyboardType = KeyboardType.Decimal,
                onValueChange = {
                    qtyState.value = it.text.takeIfDouble() ?: qtyState.value
                }
            )
            Button(
                text = "Створити",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                onConfirmClick(
                    DocumentEditContract.UIIntent.CreateItemConfirmClick(
                        name = nameState.value,
                        price = priceState.value,
                        qty = qtyState.value
                    )
                )
            }
        }
    }
}
