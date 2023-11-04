package com.practice.board.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.practice.board.NavDest
import com.practice.board.R
import com.practice.board.domain.model.Priority
import com.practice.board.ui.model.TaskUi
import com.practice.board.ui.utils.ToCanvas
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column {
        ShowTrackingBar(viewModel.trackingTask, viewModel.trackingTime)
        ShowActionButtons(viewModel.actions, navController)
        ShowTicketList(viewModel, navController)
    }
}

@Composable
private fun ShowTrackingBar(task: State<TaskUi?>, trackingTime: State<String?>) {
    val trackingTask by remember { task }
    
    Timber.d("check task. ShowTrackingBar recomposition. New tracking task: ${task.value?.title}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
            .alpha(if (trackingTask == null) 0f else 1f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = trackingTask?.title.toString())
        ShowTrackingTime(trackingTime)
        CircularProgressIndicator(
            Modifier
                .size(16.dp)
                .alpha(if (trackingTask?.isTracking == true) 1f else 0f)
        )
    }
}

@Composable
private fun ShowTrackingTime(trackingTime: State<String?>) {
    val time by remember { trackingTime }
    Timber.d("check task. ShowTrackingTime recomposition")
    Text(text = time ?: stringResource(R.string.general_emptyTime))
}

@Composable
private fun ShowActionButtons(actions: List<Action>, navController: NavController) {
    LazyRow(
        modifier = Modifier.padding(8.dp)
    ) {
        items(actions) {
            val destination = when (it) {
                Action.AddTask -> NavDest.AddTicket.getNavRoute()
            }
            ShowButton(it.toButton()) {
                Timber.d("UI - On action clicked, action: $it")
                navController.navigate(destination)
            }
        }
    }
}

@Composable
private fun ShowButton(button: ActionButton, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = stringResource(id = button.nameRes))
    }
}

@Composable
private fun ShowTicketList(viewModel: HomeViewModel, navController: NavController) {
    val tickets by remember { viewModel.tasks }
    val onDetailClicked = { task: TaskUi ->
        Timber.d("UI - On details clicked, task: $task")
        navController.navigate(NavDest.Detail.getNavRoute(task.uid))
    }

    LazyColumn {
        items(tickets) {
            ShowTicket(it, viewModel.trackingTask, viewModel::onTrackingChanged, onDetailClicked)
        }
    }
}

@Composable
private fun ShowTicket(
    task: TaskUi,
    trackingTask: State<TaskUi?>,
    onTrackingChanged: (TaskUi, Boolean) -> Unit,
    onDetailsClicked: (TaskUi) -> Unit
) {
    var isOpened by remember { mutableStateOf(false) }
    val isTracking by remember { trackingTask }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(4.dp),

    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { isOpened = !isOpened },
            horizontalArrangement = Arrangement.SpaceBetween,
            ) {
            Column() {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = if (isOpened) Int.MAX_VALUE else 1,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description.toString(),
                    maxLines = if (isOpened) Int.MAX_VALUE else 2
                )

                if (isOpened) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { onDetailsClicked(task) },
                    ) {
                        Text(text = stringResource(id = R.string.home_details))
                    }
                }
            }

            Column {
                Box() {
                    IconButton(onClick = {
                        onTrackingChanged(task, isTracking?.uid != task.uid) },
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val isActive = isTracking?.uid == task.uid
                            val trianglePath = Path().apply {
                                if (isActive) {
                                    moveTo(0f, 0f)
                                    lineTo(0f, size.height)
                                    lineTo(size.width, size.height)
                                    lineTo(size.width, 0f)
                                } else {
                                    moveTo(size.width, size.height / 2f)
                                    lineTo(0f, 0f)
                                    lineTo(0f, size.height)
                                }
                            }

                            if (isActive) {
                                drawPath(
                                    color = Color.Red,
                                    path = trianglePath
                                )
                            } else {
                                drawPath(
                                    color = Color.Black,
                                    path = trianglePath
                                )
                            }
                        }
                    }
                }

                Priority.values().find { it == task.priority }?.ToCanvas(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                        .align(Alignment.End)
                )
            }
        }
    }
}

private fun Action.toButton(): ActionButton {
    return when (this) {
        Action.AddTask -> ActionButton(this, R.string.home_action_add)
    }
}

data class ActionButton(val action: Action, val nameRes: Int)

