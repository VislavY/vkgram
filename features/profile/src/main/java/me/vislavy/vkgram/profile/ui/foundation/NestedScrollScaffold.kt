package me.vislavy.vkgram.profile.ui.foundation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

@Composable
fun NestedScrollScaffold(
    modifier: Modifier = Modifier,
    state: NestedScrollScaffoldState,
    scrollableContent: @Composable (ColumnScope.() -> Unit),
    nestedScrollableContent: @Composable (ColumnScope.() -> Unit)
) {
    Layout(
        modifier = modifier
            .scrollable(
                state = rememberScrollableState { delta ->
                    state.drag(delta)
                },
                orientation = Orientation.Vertical
            )
            .nestedScroll(state.nestedScrollConnection),
        content = {
            Column {
                scrollableContent()
            }

            Column {
                nestedScrollableContent()
            }
        }
    ) { measurables, constrains ->
        layout(constrains.maxWidth, constrains.maxHeight) {
            val scrollableContentPlaceable = measurables[0].measure(constrains.copy(maxHeight = Constraints.Infinity))
            scrollableContentPlaceable.place(0, state.offset.roundToInt())
            state.updateBounds(-(scrollableContentPlaceable.height.toFloat()))

            val nestedScrollableContentPlaceable = measurables[1].measure(constrains.copy(maxHeight = constrains.maxHeight))
            nestedScrollableContentPlaceable.place(
                x = 0,
                y = scrollableContentPlaceable.height + state.offset.roundToInt()
            )
        }
    }
}