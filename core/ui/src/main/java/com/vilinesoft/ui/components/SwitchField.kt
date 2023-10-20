package com.vilinesoft.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.PereoblikTheme


@Composable
fun SwitchField(
    titleString: String,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
    checked: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick(checked) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier.padding(top = 12.dp),
            fontSize = FontSize.large,
            text = titleString
        )
        Switch(
            checked = checked,
            onCheckedChange = { onClick(checked)},
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.secondary,
                checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

    }

}

@Preview
@Composable
fun PreviewSwitchField() {
    PereoblikTheme {
        SwitchField(titleString = "тестова", onClick = {}, checked = false)
    }
}

