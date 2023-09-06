package com.vilinesoft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.Icon
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
fun Button(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = DefaultShape,
    icon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    focusable: Boolean = false,
    fontSize: TextUnit = FontSize.medium,
    textColor: Color = LocalContentColor.current,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .background(
            color = backgroundColor,
            shape = shape
        )
        .focusProperties { canFocus = focusable }
        .clickableBounded(shape = shape) {
            onClick()
        }
        .padding(10.dp)
    ) {
        Row(modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                maxLines = 1,
                minLines = 1,
                text = text
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .height(22.dp)
                        .align(Alignment.CenterVertically),
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewButton() {
    PereoblikTheme {
        Button(text = "Text", icon = Icon.delete) {}
    }
}