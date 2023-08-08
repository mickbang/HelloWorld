package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val list = mutableListOf<Message2>()
            for (index in 0 until 20) {
                list.add(Message2(index, "san Zhang", "This is a test message!"))
            }
            Conversation(messages = list)
        }
    }

}

data class Message2(
    val id: Int,
    val author: String,
    val message: String,
    var isExpand: Boolean = false
)

@Composable
fun MessageCard2(message: Message2) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(message.isExpand) }

        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
            message.isExpand = isExpanded
        }) {
            Text(text = message.author)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.message,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard2() {
    MessageCard2(message = Message2(0, "Colleague", "Take a look at Jetpack"))
}

@Composable
fun Conversation(messages: List<Message2>) {
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        state = rememberLazyListState()
    ) {
        items(messages, key = { message -> message.id }) { message ->
            MessageCard2(message = message)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
fun PreviewConversation1() {
    val list = mutableListOf<Message2>()
    for (index in 0 until 100) {
        list.add(
            Message2(
                index,
                "san Zhang",
                "只包含一条消息的聊天会略显孤单，因此请更改对话，使其包含多条消息。您需要创建一个可显示多条消息的 Conversation 函数。对于此用例，请使用 Compose 的 LazyColumn 和 LazyRow。这些可组合项只会呈现屏幕上显示的元素，因此，对于较长的列表，使用它们会非常高效。$index"
            )
        )
    }
    Conversation(messages = list)
}