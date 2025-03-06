package com.example.animationscompose.components

import android.opengl.Visibility
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random.Default.nextInt

@Composable
fun ColorAnimationSimple(modifier: Modifier) {
    var firstColor by rememberSaveable { mutableStateOf(false) }
    var showBox by rememberSaveable { mutableStateOf(true) }
    val realColor by animateColorAsState(
        targetValue = if (firstColor) Color.Red else Color.Blue, animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ), finishedListener = {
            showBox = false
        }
    )
    if (showBox) {
        Box(
            modifier = modifier
                .size(100.dp)
                .background(realColor)
                .clickable { firstColor = !firstColor }) {

        }
    }
}

@Composable
fun SizeAnimation() {
    var smallSize by rememberSaveable {
        mutableStateOf(true)
    }
    val size by animateDpAsState(
        targetValue = if (smallSize) 50.dp else 100.dp, animationSpec = tween(
            durationMillis = 500
        ), finishedListener = {
            if (!smallSize) {
                TODO()
            }
        }
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(Color.Cyan)
            .clickable { smallSize = !smallSize }) {

    }
}

@Composable
fun VisibilityBasic() {
    val isVisible = rememberSaveable {
        MutableTransitionState(true)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isVisible.targetState = !isVisible.targetState }) {
            Text(text = "Mostrar/Ocultar")
        }
        Spacer(modifier = Modifier.size(50.dp))
        AnimatedVisibility(
            visibleState = isVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutHorizontally(
                targetOffsetX = { it * 2 },
                animationSpec = tween(
                    durationMillis = 700
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun CrossFadeExample() {
    var myComponentType: ComponentType by rememberSaveable {
        mutableStateOf(ComponentType.Text)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { myComponentType = getComponentTypeRandom() }) {
            Text("Cambiar componente")
        }
        Crossfade(targetState = myComponentType, label = "") { myComponentType ->
            when (myComponentType) {
                ComponentType.Image -> Icon(Icons.Default.Face, contentDescription = "Face")
                ComponentType.Text -> Text("Hello Mr Ruben")
                ComponentType.Box -> Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                ComponentType.Error -> Text("Error")
            }
        }
    }
}

fun getComponentTypeRandom(): ComponentType {
    return when ( nextInt(from = 0, until = 3)) {
        0 -> ComponentType.Image
        1 -> ComponentType.Text
        2 -> ComponentType.Box
        else -> ComponentType.Error
    }
}

enum class ComponentType() {
    Image, Text, Box, Error
}
