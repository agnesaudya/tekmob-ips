package com.example.ipsapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ipsapp.database.BssidDatabase
import com.example.ipsapp.database.FingerprintDatabase
import com.example.ipsapp.entity.Fingerprint
import com.example.ipsapp.ui.theme.IpsAppTheme
import com.example.ipsapp.utils.KNNClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LayoutDaerah : ComponentActivity() {
    private val midpointMap: HashMap<String, Pair<Int, Int>> = HashMap()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val finger_db = FingerprintDatabase.getInstance(this.applicationContext)
        val bssid_db = BssidDatabase.getInstance(this.applicationContext)

        setContent {
            var midpointValue by remember { mutableStateOf("") }

            IpsAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LayoutKelas()

                    Column(modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp), verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(modifier = Modifier.size(200.dp), painter = painterResource(id = R.drawable.indoortrac), contentDescription = "IndoorTrac")
                    }

                    Column(modifier = Modifier.padding(horizontal = 0.dp, vertical = 100.dp), verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        refreshButton(finger_db, bssid_db, context = LocalContext.current, midpointValue = midpointValue, onMidpointChange = {midpointValue = it})

                    }

                    DisposableEffect(midpointValue) {
                        onDispose {
                            println("Midpoint Value: $midpointValue")
                        }

                    }
                    changeCirclePosition(midpointValue)



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
fun changeCirclePosition(label: String) {
    var midpointMap: HashMap<String, Pair<Int, Int>> = HashMap()
    midpointMap.put("R1C1", Pair(70, 240))
    midpointMap.put("R1C2", Pair(150, 240))
    midpointMap.put("R1C3", Pair(230, 240))
    midpointMap.put("R1C4", Pair(310, 240))
    midpointMap.put("R2C1", Pair(70, 320))
    midpointMap.put("R2C2", Pair(150, 320))
    midpointMap.put("R2C3", Pair(230, 320))
    midpointMap.put("R2C4", Pair(310, 320))
    midpointMap.put("R3C1", Pair(70, 400))
    midpointMap.put("R3C2", Pair(150, 400))
    midpointMap.put("R3C3", Pair(230, 400))
    midpointMap.put("R3C4", Pair(310, 400))
    midpointMap.put("R4C1", Pair(70, 480))
    midpointMap.put("R4C2", Pair(150, 480))
    midpointMap.put("R4C3", Pair(230, 480))
    midpointMap.put("R4C4", Pair(310, 480))
//    if label not in midpointMap
    if (!midpointMap.containsKey(label) or label.equals("RxCx")) {
        Column(modifier = Modifier.padding(horizontal = 0.dp, vertical = 159.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text ="You are not in the class", color = Color.Red)

        }

    }
    else{
        val targetX = midpointMap[label]!!.first
        val targetY = midpointMap[label]!!.second

        Canvas(modifier = Modifier) {
            drawCircle(
                color = Color.Green,
                radius = 10.dp.toPx(),
                center = Offset(x = targetX.dp.toPx(), y = targetY.dp.toPx())
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun refreshButton(finger_db: FingerprintDatabase, bssid_db: BssidDatabase, context: Context, midpointValue: String, onMidpointChange: (String) -> Unit) {
    Button(modifier = Modifier.size(width = 150.dp, height = 50.dp), onClick = { performKNN(finger_db, bssid_db,context,midpointValue, onMidpointChange) }) {
        Text(text = "Refresh")
    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun refreshButton2(finger_db: FingerprintDatabase, bssid_db: BssidDatabase, context: Context, midpointValue: String, onMidpointChange: (String) -> Unit) {
    Button(modifier = Modifier.size(width = 150.dp, height = 50.dp), onClick = { performKNN(finger_db, bssid_db,context,midpointValue, onMidpointChange) }) {
        Text(text = "Refresh")
    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun performKNN(
    finger_db: FingerprintDatabase,
    bssid_db: BssidDatabase,
    context: Context,
    midpointValue: String,
    onMidpointChange: (String) -> Unit
) {

    GlobalScope.launch(Dispatchers.IO) {

        val fingerprintList = finger_db.fingerprintDao().getAll()
        val knnClassifier = KNNClassifier(fingerprintList)
        try {

            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CHANGE_WIFI_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {


                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager


                val scanResults: List<ScanResult> = wifiManager.scanResults
                val filteredResults = scanResults.filter { scanResult ->
                    scanResult.wifiSsid.toString() == "\"eduroam\"" || scanResult.wifiSsid.toString()
                        .contains("\"HotSpot - UI\"")
                }

                var fingerprint = Fingerprint()
                filteredResults.forEach { filteredResult ->

                    val existingBSSID = bssid_db.bssidDao().findByBSSID(filteredResult.BSSID)


                    if (existingBSSID != null) {

                        when (existingBSSID.name) {
                            "bssid1" -> fingerprint.bssid1_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid2" -> fingerprint.bssid2_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid3" -> fingerprint.bssid3_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid4" -> fingerprint.bssid4_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid5" -> fingerprint.bssid5_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid6" -> fingerprint.bssid6_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid7" -> fingerprint.bssid7_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid8" -> fingerprint.bssid8_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid9" -> fingerprint.bssid9_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid10" -> fingerprint.bssid10_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid11" -> fingerprint.bssid11_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid12" -> fingerprint.bssid12_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid13" -> fingerprint.bssid13_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid14" -> fingerprint.bssid14_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid15" -> fingerprint.bssid15_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid16" -> fingerprint.bssid16_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid17" -> fingerprint.bssid17_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid18" -> fingerprint.bssid18_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid19" -> fingerprint.bssid19_rssi = convertDbTomWatt(filteredResult.level)
                            "bssid20" -> fingerprint.bssid20_rssi = convertDbTomWatt(filteredResult.level)


                            else -> {
                                print("BSSID Not existing")
                            }
                        }


                    }
                }

                var result = knnClassifier.classify(listOf(fingerprint.bssid1_rssi,fingerprint.bssid2_rssi,
                    fingerprint.bssid3_rssi,fingerprint.bssid4_rssi,fingerprint.bssid5_rssi,
                    fingerprint.bssid6_rssi,fingerprint.bssid7_rssi, fingerprint.bssid8_rssi,
                    fingerprint.bssid9_rssi,fingerprint.bssid10_rssi,
                    fingerprint.bssid11_rssi,fingerprint.bssid12_rssi, fingerprint.bssid13_rssi,
                    fingerprint.bssid14_rssi, fingerprint.bssid15_rssi, fingerprint.bssid16_rssi,
                    fingerprint.bssid17_rssi, fingerprint.bssid18_rssi, fingerprint.bssid19_rssi,
                    fingerprint.bssid20_rssi),4)

                launch(Dispatchers.Main) {
                    onMidpointChange(result)
                }
            }else{
            }

        } catch (e: Exception) {
            e.printStackTrace()

        }


    }
}
