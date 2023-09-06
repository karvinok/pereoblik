package com.vilinesoft.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.theme.DefaultShape
import com.vilinesoft.ui.theme.FontSize
import com.vilinesoft.ui.theme.LinearBrush
import com.vilinesoft.ui.theme.PereoblikTheme

@Composable
fun TextField(
    value: String?,
    modifier: Modifier = Modifier,
    titleString: String? = null,
    subtitle: String? = null,
    hint: String? = null,
    singleLine: Boolean = true,
    isEnabled: Boolean = true,
    errorText: String? = null,
    typeOfContent: String? = null,
    focusedByDefault: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onActionDone: (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val isError = !errorText.isNullOrBlank() && isEnabled
    val focusManager = LocalFocusManager.current
    val tfValue = TextFieldValue(text = value?: titleString?: "", selection = TextRange(value?.length?: 0))

    val focusRequester = FocusRequester()

    if (focusedByDefault) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier
            .alpha(if (isEnabled) 1f else 0.5f)
            .wrapContentHeight(),
    ) {
        titleString?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FieldTitle(titleString = it)
                if (!subtitle.isNullOrBlank()) {
                    Spacer(modifier = Modifier.size(8.dp))
                    FieldSubtitle(subtitle)
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        brush = LinearBrush,
                        shape = DefaultShape
                    ).run {
                        if (isFocused || isError) {
                            border(
                                border = BorderStroke(
                                    width = if (isError) 2.dp else 1.dp,
                                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                ),
                                shape = DefaultShape
                            )
                        } else this
                    }
            ) {
                if (!hint.isNullOrBlank() && value.isNullOrBlank()) {
                    Text(
                        text = hint,
                        modifier = Modifier
                            .padding(12.dp),
                        color = LocalContentColor.current.copy(alpha = 0.2f),
                        fontSize = FontSize.medium
                    )
                }
                BasicTextField(
                    value = tfValue,
                    onValueChange = onValueChange,
                    modifier = modifier
                        .arrowFocusEvents(focusManager, onActionDone)
                        .padding(12.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onActionDone?.invoke()
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    singleLine = singleLine,
                    textStyle = TextStyle(
                        fontSize = FontSize.large,
                        color = LocalContentColor.current
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    interactionSource = interactionSource
                )
            }
            typeOfContent?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    fontSize = FontSize.small
                )
            }
        }
        if (isError) {
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = errorText.toString(),
                color = MaterialTheme.colorScheme.error,
                fontSize = FontSize.small
            )
        }
    }
}



@Composable
fun FieldTitle(titleString: String, color: Color = LocalContentColor.current) {
    Text(
        text = titleString,
        maxLines = 1,
        minLines = 1,
        style = TextStyle(
            fontSize = FontSize.medium,
            color = color,
        ),
    )
}

@Composable
fun FieldSubtitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.alpha(0.5f),
        style = TextStyle(
            fontSize = FontSize.small,
        ),
        maxLines = 1
    )
}

@Preview
@Composable
fun PreviewTextField() {
    PereoblikTheme {
        TextField(value = "Text", onValueChange = {})
    }
}