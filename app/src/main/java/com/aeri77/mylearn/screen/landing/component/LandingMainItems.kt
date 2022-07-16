package com.aeri77.mylearn.screen.landing.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aeri77.mylearn.R

@Composable
fun LandingMainItems(
    title: String,
    desc: String,
    image: Painter,
    backgroundColor: Color,
    textColor: Color
) {
    Surface {
        Column(
            modifier = Modifier
                .background(
                    backgroundColor
                )
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.W500
            )

            Image(
                modifier = Modifier.height(326.dp),
                painter = image,
                contentDescription = null
            )

            Text(
                text = desc,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W300
            )
        }
    }
}

@Preview
@Composable
fun LandingMainItemsPreview(){
    LandingMainItems(title = "Cras Augue", desc = "ababda w.. kkak lwlasdda. opreadmmawrae", image = painterResource(id = R.drawable.onboard_image_1), backgroundColor = MaterialTheme.colorScheme.primary, textColor = MaterialTheme.colorScheme.onPrimary )
}