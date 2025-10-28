package com.example.watertracker

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class DayReport(
    val day: String,
    var total: Int,
    val containers: List<String>
)
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Container(
    val name: String,
    val ml: Int,
    val uuid: String
)
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Settings(
    val goal: Int,
    val containers: List<Container>
)

class Report(private val context: Context, private val fileName: String = "days_report.json") {
    var days: MutableList<DayReport> = try {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            val json = file.readText()
            Json.decodeFromString(json)
        } else {
            val inputStream = context.assets.open(fileName)
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString(json)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        mutableListOf()
    }
    val settings: Settings = try {
        val file = File(context.filesDir, "settings.json")
        if (file.exists()) {
            val json = file.readText()
            Json.decodeFromString(json)
        } else {
            val inputStream = context.assets.open("settings.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString(json)
        }
    } catch (e: IOException) { throw e }

    fun find(day: String): DayReport {
        val existing = days.find { it.day == day }
        if (existing != null) return existing

        val newReport = DayReport(day = day, total = 0, containers = emptyList())
        days.add(newReport)
        save()
        return newReport
    }
    fun addMl(day: String, ml: Int){
        val report = find(day)
        report.total += ml
        save()
    }


    fun save() {
        val json = Json.encodeToString(ListSerializer(DayReport.serializer()), days)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }
}
