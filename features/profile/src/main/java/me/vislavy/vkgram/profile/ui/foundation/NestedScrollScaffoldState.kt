package me.vislavy.vkgram.profile.ui.foundation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.withSign

@Composable
fun rememberNestedScrollScaffoldState(): NestedScrollScaffoldState {
    val scope = rememberCoroutineScope()
    val saver = remember {
        NestedScrollScaffoldState.saver(scope = scope)
    }
    return rememberSaveable(
        saver = saver
    ) {
        NestedScrollScaffoldState(scope = scope)
    }
}

@Stable
class NestedScrollScaffoldState(
    private val scope: CoroutineScope,
    initialOffset: Float = 0F,
    initialMaxOffset: Float = 0F,
) {
    private val _maxOffset = mutableStateOf(initialMaxOffset)
    private var _offset = Animatable(initialOffset)
    val offset: Float
        get() = _offset.value
    private var changes = 0F

    internal val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y < 0) {
                return Offset(0F, drag(available.y))
            }

            return Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (available.y > 0) {
                Offset(0f, drag(consumed.y))
            }

            return Offset.Zero
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            return Velocity(0f, fling(available.y))
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            return Velocity(0f, fling(available.y))
        }
    }

    suspend fun fling(velocity: Float): Float {
        if (velocity == 0f || velocity > 0 && offset == 0f) {
            return velocity
        }

        val realVelocity = velocity.withSign(changes)
        changes = 0f

        return if (offset > _maxOffset.value && offset <= 0) {
            _offset.animateDecay(
                realVelocity,
                exponentialDecay()
            ).endState.velocity.let {
                if (offset == 0f) {
                    velocity
                } else {
                    it
                }
            }
        } else {
            0f
        }
    }

    fun updateBounds(maxOffset: Float) {
        _maxOffset.value = maxOffset
        _offset.updateBounds(maxOffset, 0f)
    }

    fun drag(delta: Float): Float {
        if (delta < 0F && offset > _maxOffset.value || delta > 0F && offset < 0F) {
            changes = delta

            scope.launch {
                _offset.snapTo((offset + delta).coerceIn(_maxOffset.value, 0f))
            }

            return delta
        }

        return 0F
    }

    companion object {
        fun saver(
            scope: CoroutineScope,
        ): Saver<NestedScrollScaffoldState, *> = listSaver(
            save = {
                listOf(it.offset, it._maxOffset.value)
            },
            restore = {
                NestedScrollScaffoldState(
                    scope = scope,
                    initialOffset = it[0],
                    initialMaxOffset = it[1],
                )
            }
        )
    }
}