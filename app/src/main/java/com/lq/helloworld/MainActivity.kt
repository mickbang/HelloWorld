package com.lq.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                MessageCard(msg = "li")
                TwoMessageCard(message = Message("王", 12))
            }
        }
    }

    @Composable
    fun conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message: Message ->
                MessageCard(msg = message.author)
            }
        }
    }


    @Preview
    @Composable
    fun conversation() {

    }


    data class Message(val author: String, val age: Int)

    @Composable
    fun TwoMessageCard(message: Message) {
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "图片"
            )
            Column {
                Text(text = "name:${message.author}")
                Text(text = "age:${message.age}")
            }
        }
    }

    @Composable
    fun MessageCard(msg: String) {
        Box{
            var count by remember { mutableStateOf(0) }
            Button(onClick = {
                count++
            },Modifier.width(100.dp)) {
                Text(text = "我是按钮$count")
            }
        }

    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard(msg = "张三")
    }

    @Preview
    @Composable
    fun PreviewTwoMessageCard() {
        TwoMessageCard(message = Message("王", 12))
    }

}


