package com.example.watertracker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watertracker.Container
import kotlin.collections.forEach

@Composable
fun WaterOptionButton(container: Container, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, contentDescription = "Kubek") },
        text = { Text("${container.name} (${container.ml} ml)") },
        modifier = modifier.fillMaxWidth()
    )
}
@Composable
fun AddWaterDialog(
    containers: List<Container>,
    onClose: () -> Unit,
    onAdd: (String, Int) -> Unit
) {

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Wybierz ilość w ml") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                containers.forEach { container ->
                    WaterOptionButton(
                        container = container,
                        onClick = {
                            onAdd(container.uuid, container.ml)
                            onClose()
                        }
                    )
                }
            }
        },
        confirmButton = {}
    )
}