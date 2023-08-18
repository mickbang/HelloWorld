package com.lq.helloworld.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lq.helloworld.R
import androidx.compose.ui.unit.sp as sp1

class GuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.padding(16.dp, 0.dp)) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (image) = createRefs()
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "logo",
                        modifier = Modifier.constrainAs(image) {
                            absoluteRight.linkTo(parent.absoluteRight)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.pic_guide),
                    contentDescription = "guide pic",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Great Installation Job, Right Now.",
                    style = TextStyle(
                        fontSize = 32.sp1,
                        lineHeight = 44.8.sp1,
//                        fontFamily = FontFamily(Font(R.font.lato)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF23262B),
                    )
                )

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (button) = createRefs()

                    FilledIconButton(
                        onClick = {
                            LoginActivity.start(this@GuideActivity)
                            finish()
                        },

                        colors = IconButtonDefaults.iconButtonColors(
                            Color(0xFF005D2D),
                            Color(0xFF005D2D),
                            Color(0xFF005D2D),
                            Color(0xFF005D2D)
                        ),
                        modifier = Modifier
                            .width(58.dp)
                            .height(58.dp)
                            .constrainAs(button) {
                                absoluteRight.linkTo(parent.absoluteRight)
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "arrow"
                        )
                    }

                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}