package com.lq.helloworld

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                TextFieldDemo()

                val messages = mutableListOf<Message>()
                for (index in 0 until 20) {
                    messages.add(
                        Message(
                            "zhang san $index",
                            "body"
                        )
                    )
                }
                Conversation(messages = messages)
            }
        }
    }
}

data class Message(val author: String, val body: String)


@Composable
fun MessageCard(msg: Message) {
    Row(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 10.dp,
            end = 16.dp,
            bottom = 10.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "dog content"
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(Message("Zhang san", "Strong"))
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    val messages = mutableListOf<Message>()
    for (index in 0 until 20) {
        messages.add(
            Message(
                "zhang san $index",
                "body"
            )
        )
    }
    Conversation(messages = messages)
}


@Composable
fun TextFieldDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        label = { Text(text = "Search") },
        leadingIcon = @Composable {
            Image(
                imageVector = Icons.Filled.Search, contentDescription = "search",
                modifier = Modifier.clickable {
                    Log.d(TAG, "TextFieldDemo: search$text")
                }
            )
        },
        trailingIcon = @Composable {
            Image(
                imageVector = Icons.Filled.Clear,
                contentDescription = "clear",
                modifier = Modifier.clickable {
                    text = ""
                    Log.d(TAG, "TextFieldDemo: clear$text")
                })
        },
        singleLine = true
    )
}

@Preview
@Composable
fun PreviewTextFieldDemo() {
    TextFieldDemo()
}

