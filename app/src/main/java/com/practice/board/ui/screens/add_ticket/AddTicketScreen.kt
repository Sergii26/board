package com.practice.board.ui.screens.add_ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.practice.board.R
import com.practice.board.domain.model.Priority
import com.practice.board.ui.screens.home.ActionButton

@Composable
fun AddTicketScreen(
    navController: NavController,
    viewModel: AddTicketViewModel = hiltViewModel()
) {
    ShowAddTicket(navController, viewModel.priorities) { title: String, description: String, priority: Priority ->
        viewModel.onSubmit(title, description, priority)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ShowAddTicket(
    navController: NavController,
    priorities: List<Priority>,
    onSubmit: (title: String, description: String, priority: Priority) -> Unit
) {
    var editedField by remember { mutableStateOf(EditedField.Title) }
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var isPriorityExpanded by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(Priority.Medium) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                label = { Text(stringResource(R.string.add_ticket_label_title)) },
                value = titleText,
                onValueChange = { titleText = it },
                textStyle = TextStyle(fontWeight = FontWeight.Bold),
                maxLines = if (editedField == EditedField.Title) Int.MAX_VALUE else 1,
                modifier = Modifier.onFocusChanged { if (it.isFocused) editedField = EditedField.Title }
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(stringResource(R.string.add_ticket_label_description)) },
                value = descriptionText,
                onValueChange = { descriptionText = it },
                maxLines = if (editedField == EditedField.Description) Int.MAX_VALUE else 1,
                modifier = Modifier.onFocusChanged { if (it.isFocused) editedField = EditedField.Description }
            )

            Spacer(modifier = Modifier.height(4.dp))

            ExposedDropdownMenuBox(
                expanded = isPriorityExpanded,
                onExpandedChange = { isPriorityExpanded = !isPriorityExpanded },
                modifier = Modifier.onFocusChanged { if (it.isFocused) editedField = EditedField.Priority }
            ) {
                OutlinedTextField(
                    label = { Text(stringResource(R.string.add_ticket_label_priority)) },
                    value = selectedPriority.name,
                    onValueChange = {},
                    maxLines = 1,
                    readOnly = true,
                )
                ExposedDropdownMenu(
                    expanded = isPriorityExpanded,
                    onDismissRequest = { isPriorityExpanded = false },
                ) {
                    priorities.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.name) },
                            onClick = {
                                selectedPriority = it
                                isPriorityExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(onClick = {
            onSubmit(titleText, descriptionText, selectedPriority)
            navController.popBackStack()
        }) {
            Text(text = stringResource(R.string.add_ticket_button_submit))
        }
    }
}

private enum class EditedField {
    Title,
    Description,
    Priority
}