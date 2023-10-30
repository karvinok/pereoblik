package com.vilinesoft.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vilinesoft.ui.keyprocessing.KeyEventHandler

data class WidthOffset(val width: Int, val offset: Int)

fun Modifier.onPlaced(state: MutableState<WidthOffset>) = onPlaced {
    state.value = WidthOffset(it.size.width, it.positionInParent().x.toInt())
}

fun Modifier.onPlacedOffset(offset: MutableState<Offset>) = onPlaced {
    offset.value = Offset(it.positionInParent().x, it.positionInParent().y)
}

fun Modifier.clickableBounded(shape: Shape, onClick: () -> Unit) = composed {
    clip(shape = shape).clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(
            bounded = true,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
        ),
        onClick = onClick
    )
}

fun Modifier.clickableUnbounded(onClick: () -> Unit) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(
            bounded = false,
            radius = 30.dp,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
        ),
        onClick = onClick
    )
}

fun Modifier.clickableNoIndication(onClick: (() -> Unit)? = null) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick?: {}
    )
}

fun Modifier.defaultShadow(
    shape: Shape = RectangleShape,
    clip: Boolean = false,
    elevation: Dp = 5.dp,
) = shadow(
    elevation,
    shape,
    clip,
    DefaultShadowColor,
    DefaultShadowColor,
)

@Composable
fun Modifier.arrowFocusEvents(
    focusManager: FocusManager = LocalFocusManager.current,
    onActionDone: (() -> Unit)? = null
) = composed {
    onPreviewKeyEvent {
        when {
            it.type == KeyEventType.KeyDown && it.key == Key.DirectionUp ->
                focusManager.moveFocus(FocusDirection.Up)

            it.type == KeyEventType.KeyDown && it.key == Key.DirectionDown ->
                focusManager.moveFocus(FocusDirection.Down)

            it.type == KeyEventType.KeyDown && it.key == Key.Tab ->
                focusManager.moveFocus(FocusDirection.Down)

            it.type == KeyEventType.KeyDown && it.key == Key.Enter -> {
                onActionDone?.invoke()
                true
            }

            else -> false
        }
    }
}

@Composable
fun Modifier.keyEventHandler(
    keyEventHandler: KeyEventHandler
) = composed {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    focusRequester(focusRequester)
        .onPreviewKeyEvent {
            keyEventHandler.handleEvent(it.nativeKeyEvent)
            true
        }
        .focusable(true)
}
