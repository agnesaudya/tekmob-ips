package com.example.ilsapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.ilsapp.database.FingerprintDatabase
import com.example.ilsapp.entity.Fingerprint
import com.example.ilsapp.ui.theme.IlsAppTheme
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_WIFI_STATE), 1)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        }

        val db = Room.databaseBuilder(
            applicationContext,
            FingerprintDatabase::class.java, "fingerprint"

        ).build()
        setContent {
            IlsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputScreen(db)
                }
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//
//    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//    val wifiInfo: WifiInfo? = wifiManager.connectionInfo
//    var scanResults: List<ScanResult> = emptyList()
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//        == PackageManager.PERMISSION_GRANTED) {
//        scanResults = wifiManager.scanResults
//    }
//    Column {
//        Text("Connected Wi-Fi: ${wifiInfo?.ssid} (RSSI: ${wifiInfo?.rssi})")
//        Text("Available Wi-Fi Networks:")
//        scanResults.forEach { scanResult ->
//            Text("SSID: ${scanResult.wifiSsid}, RSSI: ${scanResult.level}")
//        }
//    }
//
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputLayout(modifier: Modifier, label:String, row:String, col:String, onLabelChange: (String) -> Unit,
                onRowChange: (String) -> Unit,
                onColChange: (String) -> Unit) {


    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .align(alignment = Alignment.CenterHorizontally),
                text = stringResource(R.string.title, 0),
                style = typography.titleMedium,
                color = colorScheme.inversePrimary
            )
            OutlinedTextField(
                value = label,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onLabelChange,
                label = { Text(stringResource(R.string.label)) }
            )
            OutlinedTextField(
                value = col,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onColChange,
                label = { Text(stringResource(R.string.col)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = row,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onRowChange,
                label = { Text(stringResource(R.string.row)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun InputScreen(db: FingerprintDatabase){
    var label by remember { mutableStateOf("") }
    var row by remember { mutableStateOf("") }
    var col by remember { mutableStateOf("") }
    var rssi by remember { mutableStateOf(0) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onLabelChange = { label = it },
            label = label,
            onRowChange = { row = it },
            row = row,
            onColChange = { col = it },
            col = col)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { performDatabaseAction(row, col,db, context, onRssiChange = { rssi=it }) }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 15.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { exportDataToJson(context=context, db = db) }
            ) {
                Text(
                    text = stringResource(R.string.download),
                    fontSize = 16.sp
                )
            }
        }
        RSSIScore(score = rssi, modifier = Modifier.padding(20.dp))
    }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun performDatabaseAction(row: String, col: String, db: FingerprintDatabase, context: Context, onRssiChange : (Int) -> Unit) {

    GlobalScope.launch(Dispatchers.IO) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val scanResults: List<ScanResult> = wifiManager.scanResults
            val filteredResult = scanResults.filter { scanResult ->
                scanResult.wifiSsid.toString() == "\"eduroam\"" || scanResult.wifiSsid.toString()
                    .contains("\"HotSpot - UI\"")
            }
            val sortedResult = filteredResult.sortedByDescending { scanResult -> scanResult.level }

            db.fingerprintDao().insert(Fingerprint(uid=0, label = "R"+row+"C"+col, rssi1 = sortedResult[0].level,  rssi2= sortedResult[1].level,  rssi3= sortedResult[2].level, rssi4 = sortedResult[3].level, rssi5 =  sortedResult[4].level, rssi6 = sortedResult[5].level))
//      scanResult.level insert to db -> knn
            // Extract BSSIDs from ScanResults and print them
            println("helo")
        } else {
            println("helo")
            // Request ACCESS_FINE_LOCATION permission if not granted

        }
    }

//    var scanResults: List<ScanResult>
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//        == PackageManager.PERMISSION_GRANTED
//    ) {
//        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//    }
//        scanResults = wifiManager.scanResults
//
//        scanResults.forEach { scanResult ->
//            if (ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                val info = wifiManager.connectionInfo
//                val ssid = info.ssid
//                val bssid = info.bssid
//
//                println(ssid)
//                println(bssid)
//            }


//            GlobalScope.launch(Dispatchers.IO) {
//
////                val wifiInfo: WifiInfo? = wifiManager.connectionInfo
//                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    val info = wifiManager.connectionInfo
//                    val ssid = info.ssid
//                    val bssid = info.bssid
//
//                    println(ssid)
//                    println(bssid)
////                    println(scanResult.BSSID)
////                    println(scanResult.wifiSsid)
////                    println(scanResult.level)
//
////                    val existingRecord = db.fingerprintDao().findByLabel(bssid = scanResult.wifiSsid.toString())
////                    if (existingRecord != null) {
////                        val arrayList = existingRecord.rssi
////
////                        arrayList.add(scanResult.level)
////                        existingRecord.rssi = arrayList
////                        db.fingerprintDao().update(existingRecord)
////                    } else {
////                        val arrayList = ArrayList<Int>()
////                        arrayList.add(scanResult.level)
////
////                        db.fingerprintDao().insert(Fingerprint(scanResult.wifiSsid.toString(), row.toInt(), col.toInt(), arrayList))
////                    }
//                }
//
//            }
//




}



fun exportDataToJson(context: Context, db: FingerprintDatabase) {
    GlobalScope.launch(Dispatchers.IO) {
        try {

            val fingerprintList = db.fingerprintDao().getAll()
            val gson = Gson()
            val jsonData = gson.toJson(fingerprintList)

            val fileName = "fingerprint_data.json"
            val folder = "Download"
            val existingFolderPath = Environment.getExternalStorageDirectory().absolutePath + File.separator + folder
            val filePath = existingFolderPath + File.separator + fileName

            val file = File(filePath)

            FileWriter(file).use { writer ->
                writer.write(jsonData)
            }

            println("Export to JSON successful. File path: ${file.absolutePath}")
        } catch (e: Exception) {

            println("Export to JSON failed: ${e.message}")
        }
    }
}


@Composable
fun RSSIScore(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.rssi, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

