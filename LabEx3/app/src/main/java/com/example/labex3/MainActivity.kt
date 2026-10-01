package com.example.labex3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labex3.ui.theme.LabEx3Theme

data class Message(val author: String, val body: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabEx3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MessageList(
                        messages = SampleData.conversationSample,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

object SampleData {
    val conversationSample = listOf(
        Message("Joe", "Hi!"),
        Message("Jim", "How are you?"),
        Message("Joe", "Test..1..2...3"),
        Message("Joe", "I hate coding!!!"),
        Message("Joe", "Hi!"),
        Message("Jim", "How are you?"),
        Message("Joe", "Test..1..2...3")
    )
}

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 12.dp)) {
        Surface(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Red, CircleShape),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile picture",
                modifier = Modifier.padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = msg.author,
                color = Color(0xFF676780),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF5F5F5),
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageList() {
    LabEx3Theme {
        MessageList(messages = SampleData.conversationSample)
    }
}