package com.practice.board.ui.utils

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.practice.board.domain.model.Priority
import com.practice.board.ui.theme.Red
import com.practice.board.ui.theme.Orange
import com.practice.board.ui.theme.Green

@Composable
fun Priority.ToCanvas(modifier: Modifier) {
    val color = when(this) {
        Priority.Low -> Green
        Priority.Medium -> Orange
        Priority.High -> Red
        Priority.Unknown -> Color.Transparent
    }
    Canvas(modifier = modifier) {
        drawCircle(color, radius = 8.dp.toPx())
    }
}