package pini.mattia.coachtimer.ui.customcomposables
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pini.mattia.coachtimer.ui.theme.stopwatchFontRegular

@Composable
fun MultiFloatingActionButton(
    fabIcon: ImageVector,
    items: List<MultiFabItem>,
    toState: MultiFabState,
    showLabels: Boolean = true,
    stateChanged: (fabstate: MultiFabState) -> Unit
) {
    val transition: Transition<MultiFabState> = updateTransition(targetState = toState, label = "transition animation")
    val scale: Float by transition.animateFloat(label = "") { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val alpha: Float by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "alpha animation"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val shadow: Dp by transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "shadow animation"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 2.dp else 0.dp
    }
    val rotation: Float by transition.animateFloat(label = "rotation animation") { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }
    Column(horizontalAlignment = Alignment.End) {
        items.forEach { item ->
            MiniFabItem(item, alpha, shadow, scale, showLabels, item.enabled, item.onFabClick)
            Spacer(modifier = Modifier.height(20.dp))
        }
        FloatingActionButton(onClick = {
            stateChanged(
                if (transition.currentState == MultiFabState.EXPANDED) {
                    MultiFabState.COLLAPSED
                } else MultiFabState.EXPANDED
            )
        }) {
            Icon(
                imageVector = fabIcon,
                modifier = Modifier.rotate(rotation),
                contentDescription = "Icon fab"
            )
        }
    }
}

@Composable
private fun MiniFabItem(
    item: MultiFabItem,
    alpha: Float,
    shadow: Dp,
    scale: Float,
    showLabel: Boolean,
    enabled: Boolean,
    onFabItemClicked: () -> Unit
) {
    val fabColor = MaterialTheme.colorScheme.primary
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        if (showLabel) {
            Text(
                item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = stopwatchFontRegular,
                color = if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray,
                modifier = Modifier
                    .alpha(animateFloatAsState(alpha).value)
                    .shadow(animateDpAsState(shadow).value)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Surface(
            modifier = Modifier.scale(animateFloatAsState(targetValue = scale).value).clip(RoundedCornerShape(24.dp))
        ) {
            IconButton(
                onClick = { onFabItemClicked() },
                enabled = enabled,
                modifier = Modifier.then(Modifier.size(48.dp))
                    .border(1.dp, fabColor, shape = CircleShape),
                colors = IconButtonDefaults.iconButtonColors(contentColor = fabColor)
            ) {
                item.icon()
            }
        }
    }
}
