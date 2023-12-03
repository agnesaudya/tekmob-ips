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
import com.example.ilsapp.database.BssidDatabase
import com.example.ilsapp.database.FingerprintDatabase
import com.example.ilsapp.entity.BSSID
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

        val finger_db = Room.databaseBuilder(
            applicationContext,
            FingerprintDatabase::class.java, "fingerprint"

        ).build()

        val bssid_db = Room.databaseBuilder(
            applicationContext,
            BssidDatabase::class.java, "bssid"

        ).build()
        setContent {
            IlsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputScreen(finger_db, bssid_db)
                }
            }
        }
    }
}



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
fun InputScreen(finger_db: FingerprintDatabase, bssid_db: BssidDatabase){
    var label by remember { mutableStateOf("") }
    var row by remember { mutableStateOf("") }
    var col by remember { mutableStateOf("") }
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
                onClick = { performDatabaseAction(row, col,finger_db,bssid_db, context) }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 15.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { exportDataToJson(context=context, db = finger_db) }
            ) {
                Text(
                    text = stringResource(R.string.download),
                    fontSize = 16.sp
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { performGlobalScan(context=context, db = bssid_db) }
            ) {
                Text(
                    text = stringResource(R.string.input),
                    fontSize = 16.sp
                )
            }
        }

    }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun performDatabaseAction(row: String, col: String, finger_db: FingerprintDatabase, bssid_db:BssidDatabase, context: Context, ) {

    GlobalScope.launch(Dispatchers.IO) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val scanResults: List<ScanResult> = wifiManager.scanResults
            val filteredResults = scanResults.filter { scanResult ->
                scanResult.wifiSsid.toString() == "\"eduroam\"" || scanResult.wifiSsid.toString()
                    .contains("\"HotSpot - UI\"")
            }

            filteredResults.forEach { filteredResult -> run {
                var fingerprint = Fingerprint()
                val existingBSSID = bssid_db.bssidDao().findByBSSID(filteredResult.BSSID)
                if(existingBSSID!=null){
                    when (existingBSSID.name) {
                        "bssid1" -> fingerprint.bssid1_rssi = filteredResult.level
                        "bssid2" -> fingerprint.bssid2_rssi = filteredResult.level
                        "bssid3" -> fingerprint.bssid3_rssi = filteredResult.level
                        "bssid4" -> fingerprint.bssid4_rssi = filteredResult.level
                        "bssid5" -> fingerprint.bssid5_rssi = filteredResult.level
                        "bssid6" -> fingerprint.bssid6_rssi = filteredResult.level
                        else -> {
                            print("BSSID Not existing")
                        }
                    }
                    fingerprint.label="R${row}C${col}"
//                    finger_db.fingerprintDao().insert(Fingerprint())
                }

            } }

//            db.fingerprintDao().insert(Fingerprint()
//      scanResult.level insert to db -> knn
            // Extract BSSIDs from ScanResults and print them

        } else {

            // Request ACCESS_FINE_LOCATION permission if not granted

        }
    }



}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun performGlobalScan(db: BssidDatabase, context: Context, ) {

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
            val sortedResults = filteredResult.sortedByDescending { scanResult -> scanResult.level }

            sortedResults.forEach {

                    sortedResult ->
                run {
                    val existingBSSID = db.bssidDao().findByBSSID(sortedResult.BSSID)
                    val counter = db.bssidDao().countTotal()
                    if(existingBSSID!=null){

                        db.bssidDao().insert(
                            BSSID(
                                uid = 0,
                                bssid = sortedResult.BSSID,
                                ssid = sortedResult.wifiSsid.toString(),
                                name = "bssid${counter+1}"
                            )
                        )

                    }
                }
            }
        } else {

            // Request ACCESS_FINE_LOCATION permission if not granted

        }
    }



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




