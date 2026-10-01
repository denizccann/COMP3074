package com.example.labex2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labex2.ui.theme.LabEx2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabEx2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ActionButtons(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}

@Composable
fun ActionButtons(modifier: Modifier = Modifier) {
    var count by remember { mutableStateOf(0) }
    var stepValue by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFA9C2E3)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "App Logo",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = count.toString(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { count -= stepValue },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp)
            ) {
                Text(text = "-", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(60.dp))

            Button(
                onClick = { count += stepValue },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp)
            ) {
                Text(text = "+", fontSize = 18.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    count = 0
                    stepValue = 1
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp)
            ) {
                Text(text = "Reset", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(60.dp))

            Button(
                onClick = {
                    stepValue = if (stepValue == 1) 2 else 1
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp)
            ) {
                Text(text = "Step", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}