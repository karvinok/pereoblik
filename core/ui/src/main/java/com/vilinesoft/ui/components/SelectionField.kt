package com.vilinesoft.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.theme.CornerRadius
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.Icon
import com.vilinesoft.ui.theme.LinearBrush
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
fun <T> SelectionField(
    titleString: String,
    selectedElementTitle: String?,
    modifier: Modifier = Modifier,
    selectedIconComposable: (@Composable (Modifier) -> Unit)? = null,
    subtitle: String? = null,
    list: List<T>,
    addExtraNonSelectedItem: Boolean = false,
    selectedBlock: (T?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        CommonTextedClickableField(
            titleString = titleString,
            selectedElementTitle = selectedElementTitle,
            modifier = modifier,
            selectedIconComposable = selectedIconComposable,
            reversedArrow = expanded,
            subtitle = subtitle,
            clickBlock = { expanded = !expanded }
        )
        if (list.isEmpty()) expanded = false

        SelectionFieldPopup(
            expanded = expanded,
            list = list,
            addExtraNonSelectedItem = addExtraNonSelectedItem,
            onDismissBlock = { expanded = false },
            clickBlock = {
                selectedBlock.invoke(it)
                expanded = false
            }
        )
    }
}

@Composable
private fun <T> SelectionFieldPopup(
    expanded: Boolean,
    list: List<T>,
    addExtraNonSelectedItem: Boolean,
    onDismissBlock: () -> Unit,
    clickBlock: (selectedId: T?) -> Unit
) {
    if (!expanded) return

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissBlock,
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        PereoblikTheme {
            if (addExtraNonSelectedItem) {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .clickable { clickBlock(null) }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Не вибрано",
                        fontSize = FontSize.large,
                    )
                }
            }
            list.forEach {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .clickable { clickBlock(it) }
                        .padding(8.dp)
                ) {
                    Text(
                        text = it.toString(),
                        fontSize = FontSize.large,
                    )
                }
            }
        }
    }
}

@Composable
private fun CommonTextedClickableField(
    titleString: String,
    modifier: Modifier = Modifier,
    textColor: Color = LocalContentColor.current,
    fontSize: TextUnit = FontSize.large,
    selectedElementTitle: String?,
    selectedIconComposable: (@Composable (Modifier) -> Unit)? = null,
    reversedArrow: Boolean = false,
    shapeRadius: Dp = CornerRadius.default,
    subtitle: String? = null,
    clickBlock: () -> Unit
) {
    CommonClickableField(
        titleString = titleString,
        modifier = modifier,
        subtitle = subtitle,
        shapeRadius = shapeRadius,
        reversedArrow = reversedArrow,
        clickBlock = clickBlock
    ) {
        selectedIconComposable?.let {
            it.invoke(Modifier.size(10.dp))
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(
            text = selectedElementTitle ?: "Не вибрано",
            color = if (selectedElementTitle != null) {
                textColor
            } else {
                textColor.copy(alpha = 0.5f)
            },
            style = TextStyle(
                fontSize = fontSize,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CommonClickableField(
    modifier: Modifier = Modifier,
    titleString: String? = null,
    subtitle: String? = null,
    reversedArrow: Boolean = false,
    altIcon: Painter? = null,
    shapeRadius: Dp = 7.dp,
    clickBlock: () -> Unit,
    content: @Composable () -> Unit
) {
    val rotation = if (reversedArrow) 180f else 0f
    val storedRotation = animateFloatAsState(rotation, tween(200), label = "")
    val storedShapeRadius = animateDpAsState(shapeRadius, tween(200), label = "")
    val defaultShapeRadius = 7.dp

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        if (!titleString.isNullOrEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FieldTitle(titleString = titleString)
                if (!subtitle.isNullOrBlank()) {
                    Spacer(modifier = Modifier.size(8.dp))
                    FieldSubtitle(subtitle)
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
        Box(
            modifier = Modifier
                .clickableBounded(DefaultShape, clickBlock)
                .background(
                    brush = LinearBrush,
                    shape = RoundedCornerShape(
                        topStart = defaultShapeRadius,
                        topEnd = defaultShapeRadius,
                        bottomStart = storedShapeRadius.value,
                        bottomEnd = storedShapeRadius.value
                    ),
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    content()
                }
                Icon(
                    painter = altIcon ?: rememberVectorPainter(Icon.arrowDown),
                    modifier = Modifier
                        .rotate(storedRotation.value)
                        .size(if (altIcon != null) 20.dp else 20.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSelectionField() {
    PereoblikTheme {
        SelectionField(
            titleString = "Title",
            selectedElementTitle = "First",
            list = listOf("First", "Second"),
            selectedBlock = {}
        )
    }
}