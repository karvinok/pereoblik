package com.vilinesoft.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vilinesoft.settings.SettingsContract.UIIntent
import com.vilinesoft.settings.SettingsContract.UIIntent.SelectStore
import com.vilinesoft.settings.SettingsContract.UIState
import com.vilinesoft.ui.components.Button
import com.vilinesoft.ui.components.SelectionField
import com.vilinesoft.ui.components.SwitchField
import com.vilinesoft.ui.components.TextField
import com.vilinesoft.ui.theme.FontSize
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsContent(
        modifier = modifier,
        state = uiState,
        intentBlock = viewModel::handleIntent
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier,
    intentBlock: (UIIntent) -> Unit,
    state: UIState,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            titleString = "IP адреса сервера",
            value = state.ipString,
            onValueChange = { intentBlock(UIIntent.IpStringChanged(it.text)) })
        TextField(
            titleString = "Префікс вагового товару",
            value = state.ipPrefix,
            onValueChange = { intentBlock(UIIntent.PrefixChanged(it.text)) })
        SwitchField(
            titleString = "Створити товар при відсутності",
            onClick = { intentBlock(UIIntent.CanCreateItemCreatingIfNotExists(it)) },
            checked = state.isCanItemCreatingIfNotExists
        )
        SwitchField(
            titleString = "Додавати товар без Штрихкоду",
            onClick = { intentBlock(UIIntent.CanCreateItemWithoutBarcodeClick(it)) },
            checked = state.isCanItemCreatingWithoutBarcode
        )
        SwitchField(
            titleString = "Можливість редагувати ім'я товару",
            onClick = { intentBlock(UIIntent.CanChangeItemNameClick(it)) },
            checked = state.isCanChangeItemName
        )
        SelectionField(titleString = "Магазин/Склад",
            selectedElementTitle = state.storeSelected.name,
            list = state.downloadedStoreList,
            selectedBlock = {
                it?.let { it1 -> SelectStore(it1) }?.let { it2 -> intentBlock(it2) }
            })
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { intentBlock(UIIntent.SaveClick) },
            text = "ЗБЕРЕГТИ"
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                modifier = modifier.weight(1.0f),
                fontSize = FontSize.medium,
                text = "Версія програми:"
            )
            Text(
                modifier = modifier.weight(1.0f),
                fontSize = FontSize.medium,
                text = "номер версії"
            )
        }

        if (state.loadingStoreList) {
            LoadingShops(modifier)
        }


    }

}

@Composable
fun LoadingShops(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            modifier = modifier.padding(top = 12.dp),
            fontSize = FontSize.small,
            fontStyle = FontStyle.Italic,
            text = "Зачекайте, йде завантаження магазинів/складів"
        )
    }

}