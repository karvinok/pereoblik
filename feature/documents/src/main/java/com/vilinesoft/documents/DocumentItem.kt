import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vilinesoft.domain.model.Document
import com.vilinesoft.ui.theme.DefaultShape

@Composable
fun DocumentItem(
    document: Document,
    modifier: Modifier = Modifier
) {
    val bgColor = if (document.isSelected)
        MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface
    val animatedStrokeWidth = animateDpAsState(
        targetValue = if (document.isSelected) 1.dp else (-1).dp,
        label = "BorderWidthAnimation",
        animationSpec = tween(300)
    )

    Column(
        modifier = modifier
            .border(
                width = animatedStrokeWidth.value,
                color = MaterialTheme.colorScheme.primary,
                shape = DefaultShape
            )
            .background(
                color = bgColor,
                shape = DefaultShape
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Док. №${document.numberDoc}")
            Text(text = document.commDoc.toString())
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = document.docType?.toName()?: "")
            Text(text = document.dateDoc.toString())
        }
    }
}
