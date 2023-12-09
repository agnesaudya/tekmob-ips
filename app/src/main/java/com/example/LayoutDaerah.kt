package com.example.ilsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ipsapp.ui.theme.IpsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IpsAppTheme {
                var midpointMap: HashMap<Int, Pair<Int, Int>> = HashMap()
                midpointMap.put(1, Pair(70, 240))
                midpointMap.put(2, Pair(150, 240))
                midpointMap.put(3, Pair(230, 240))
                midpointMap.put(4, Pair(310, 240))
                midpointMap.put(5, Pair(70, 320))
                midpointMap.put(6, Pair(150, 320))
                midpointMap.put(7, Pair(230, 320))
                midpointMap.put(8, Pair(310, 320))
                midpointMap.put(9, Pair(70, 400))
                midpointMap.put(10, Pair(150, 400))
                midpointMap.put(11, Pair(230, 400))
                midpointMap.put(12, Pair(310, 400))
                midpointMap.put(13, Pair(70, 480))
                midpointMap.put(14, Pair(150, 480))
                midpointMap.put(15, Pair(230, 480))
                midpointMap.put(16, Pair(310, 480))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LayoutKelas()
                    var locate =  midpointMap.get(11)
                    changeCirclePosition(targetX = locate!!.first, targetY = locate!!.second)
                }
            }
        }
    }
}

@Composable
fun LayoutKelas() {
    Canvas(modifier = Modifier) {

        // BARIS 1
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 30.dp.toPx(), y = 200.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 110.dp.toPx(), y = 200.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 190.dp.toPx(), y = 200.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 270.dp.toPx(), y = 200.dp.toPx())
        )

        // BARIS 2
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 30.dp.toPx(), y = 280.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 110.dp.toPx(), y = 280.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 190.dp.toPx(), y = 280.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 270.dp.toPx(), y = 280.dp.toPx())
        )

        // BARIS 3
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 30.dp.toPx(), y = 360.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 110.dp.toPx(), y = 360.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 190.dp.toPx(), y = 360.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 270.dp.toPx(), y = 360.dp.toPx())
        )

        // BARIS 4
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 30.dp.toPx(), y = 440.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 110.dp.toPx(), y = 440.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 190.dp.toPx(), y = 440.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 80.dp.toPx(), height = 80.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 270.dp.toPx(), y = 440.dp.toPx())
        )
    }
}

@Composable
fun changeCirclePosition(targetX: Int, targetY: Int) {

    Canvas(modifier = Modifier) {
        drawCircle(
            color = Color.Green,
            radius = 10.dp.toPx(),
            center = Offset(x = targetX.dp.toPx(), y = targetY.dp.toPx())
        )
    }
}