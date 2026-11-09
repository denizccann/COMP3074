package com.example.sensorlab

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var temperatureSensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null

    private var temperature by mutableStateOf<Float?>(null)
    private var gyrox by mutableStateOf(0f)
    private var gyroy by mutableStateOf(0f)
    private var gyroz by mutableStateOf(0f)
    private var temperatureAvailable by mutableStateOf(false)
    private var gyroscopeAvailable by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        temperatureAvailable = temperatureSensor != null
        gyroscopeAvailable = gyroscopeSensor != null

        setContent {
            MaterialTheme {
                SensorScreen(
                    temperature = temperature,
                    temperatureAvailable = temperatureAvailable,
                    gyrox = gyrox,
                    gyroy = gyroy,
                    gyroz = gyroz,
                    gyroscopeAvailable = gyroscopeAvailable
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.values.isEmpty()) return
        when (event.sensor.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                temperature = event.values[0]
            }
            Sensor.TYPE_GYROSCOPE -> {
                if (event.values.size >= 3) {
                    gyrox = event.values[0]
                    gyroy = event.values[1]
                    gyroz = event.values[2]
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}

@Composable
fun SensorScreen(
    temperature: Float?,
    temperatureAvailable: Boolean,
    gyrox: Float,
    gyroy: Float,
    gyroz: Float,
    gyroscopeAvailable: Boolean
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Android Sensor Monitor",
                style = MaterialTheme.typography.headlineMedium
            )
            TemperatureCard(temperature, temperatureAvailable)
            GyroscopeCard(gyrox, gyroy, gyroz, gyroscopeAvailable)
        }
    }
}

@Composable
fun TemperatureCard(temperature: Float?, available: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ambient Temperature", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            when {
                !available -> Text("Temperature sensor is not available.")
                temperature == null -> Text("Waiting for sensor reading...")
                temperature < -50f || temperature > 100f -> Text("Temperature data out of valid range.")
                else -> Text("%.1f °C".format(temperature), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Composable
fun GyroscopeCard(x: Float, y: Float, z: Float, available: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Gyroscope", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            if (!available) {
                Text("Gyroscope is not available.")
            } else {
                Text("X: %.3f rad/s".format(x))
                Text("Y: %.3f rad/s".format(y))
                Text("Z: %.3f rad/s".format(z))
            }
        }
    }
}