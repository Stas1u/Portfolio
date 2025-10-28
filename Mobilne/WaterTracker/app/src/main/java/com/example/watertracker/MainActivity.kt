package com.example.watertracker

import com.example.watertracker.component.WaterLevel
import com.example.watertracker.component.DayGoalRow
import com.example.watertracker.component.ActionButton
import com.example.watertracker.component.AddWaterDialog

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.example.watertracker.ui.theme.WaterTrackerTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.mutableStateOf
import java.util.Calendar
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        context = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(context: Context, modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val todayStr = today.format(formatter)

    var showAddWaterDialog by remember { mutableStateOf(false) }

    val report = remember { Report(context) }
    val selected = remember { mutableStateOf(report.find(todayStr)) }

    Column(modifier = modifier.fillMaxSize()) {
        DayGoalRow(
            day = selected.value.day,
            goal = "${selected.value.total}/${report.settings.goal}"
        )
        WaterLevel(
            ml = selected.value.total,
            goal = report.settings.goal
        )
        ActionButton(
            onClick = { showAddWaterDialog = true },
            text = "Dodaj wodę",
            icon = { Icon(Icons.Filled.Add, contentDescription = "Dodaj wodę") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        if (showAddWaterDialog)
            AddWaterDialog(
                containers = report.settings.containers,
                onClose = {showAddWaterDialog = false},
                onAdd = { uuid, value ->
                    selected.value.total += value
                    report.save()
                }
            )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                onClick = {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                    DatePickerDialog(context, { _, y, m, d ->
                        val monthStr = String.format("%02d", m + 1)
                        val dayStr = String.format("%02d", d)
                        selected.value = report.find("$y-$monthStr-$dayStr")
                    }, year, month, dayOfMonth).show()
                },
                text = "Zmień Dzień",
                icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Zmień Dzień") },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                onClick = {/* TO DO */},
                text = "Statystyki",
                icon = { Icon(Icons.Filled.BarChart, contentDescription = "Statystyki") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}