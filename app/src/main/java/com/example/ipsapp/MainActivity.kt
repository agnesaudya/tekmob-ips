package com.example.ipsapp
import com.example.ipsapp.LayoutDaerah
import android.Manifest
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

import com.example.ipsapp.database.BssidDatabase
import com.example.ipsapp.database.FingerprintDatabase
import com.example.ipsapp.entity.BSSID
import com.example.ipsapp.entity.Fingerprint
import com.example.ipsapp.ui.theme.IpsAppTheme
import com.example.ipsapp.utils.KNNClassifier
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import kotlin.math.pow


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

//        val finger_db = Room.databaseBuilder(
//            applicationContext,
//            FingerprintDatabase::class.java, "fingerprint"
//
//        ).build()
        val finger_db = FingerprintDatabase.getInstance(this.applicationContext)
        val bssid_db = BssidDatabase.getInstance(this.applicationContext)
        setContent {
            IpsAppTheme {
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
fun InputLayout(modifier: Modifier,  row:String, col:String,
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
    var row by remember { mutableStateOf("") }
    var col by remember { mutableStateOf("") }
    val snackState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope()

    fun launchSnackbar(message: String, actionLabel : String?=null, duration: SnackbarDuration = SnackbarDuration.Short){
        scope.launch {
            snackState.showSnackbar(message = message,actionLabel=actionLabel, duration=duration)
        }
    }


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
                onClick = {
                    if (row.isNotBlank() && col.isNotBlank()) {
                        performDatabaseAction(row, col, finger_db, bssid_db, context) { res ->
                            launchSnackbar(
                                message = res,
                                actionLabel = "Hide",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        launchSnackbar(
                            message = "Please fill in both row and column",
                            actionLabel = "Hide",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 15.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { exportDataToJson(db = finger_db) }
            ) {
                Text(
                    text = stringResource(R.string.download),
                    fontSize = 16.sp
                )
            }

            Box(modifier = Modifier.fillMaxSize(), Alignment.BottomCenter){
                SnackbarHost(hostState = snackState)
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

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dumpBssidDataToJson(db = bssid_db) }
            ) {
                Text(
                    text = stringResource(R.string.dump),
                    fontSize = 16.sp
                )
            }
//            Button(
//                modifier = Modifier.fillMaxWidth(),
//                onClick = { performKNN(finger_db, bssid_db,context ) }
//            )
            Button(  modifier = Modifier.fillMaxWidth(),onClick = {
                context.startActivity(Intent(context, LayoutDaerah::class.java))
            })
            {
                Text(
                    text = stringResource(R.string.map),
                    fontSize = 16.sp
                )
            }
        }

    }


}



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun performDatabaseAction(
        row: String,
        col: String,
        finger_db: FingerprintDatabase,
        bssid_db: BssidDatabase,
        context: Context,
        callback: (String) -> Unit
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                println(row)
                println(col)
                val f = finger_db.fingerprintDao().findByLabel("R${row}C${col}")
                val b = bssid_db.bssidDao().findByBSSID("f0:5c:19:85:9c:41")

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
                    val success = wifiManager.startScan()

                    if (!success) {

                        callback.invoke("Gagal scan, hasil masih sama")
                        return@launch //keluar dari coroutine
                    }else{

                        callback.invoke("sukses $success")
                    }

                    val scanResults: List<ScanResult> = wifiManager.scanResults
                    val filteredResults = scanResults.filter { scanResult ->
                        scanResult.wifiSsid.toString() == "\"eduroam\"" || scanResult.wifiSsid.toString()
                            .contains("\"HotSpot - UI\"")
                    }

                    var fingerprint = Fingerprint()
                    filteredResults.forEach { filteredResult ->


                        val existingBSSID = bssid_db.bssidDao().findByBSSID(filteredResult.BSSID)

                        println(filteredResult.BSSID)
                        if (existingBSSID != null) {
                            println(existingBSSID.name)
                            println(filteredResult.level)
                            println(convertDbToDbWatt(filteredResult.level))

//f0:5c:19:85:9c:41 f0:5c:19:85:9c:41
                            when (existingBSSID.name) {
                                "bssid1" -> fingerprint.bssid1_rssi = convertDbToDbWatt(filteredResult.level)
                                "bssid2" -> fingerprint.bssid2_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid3" -> fingerprint.bssid3_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid4" -> fingerprint.bssid4_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid5" -> fingerprint.bssid5_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid6" -> fingerprint.bssid6_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid7" -> fingerprint.bssid7_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid8" -> fingerprint.bssid8_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid9" -> fingerprint.bssid9_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid10" -> fingerprint.bssid10_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid11" -> fingerprint.bssid11_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid12" -> fingerprint.bssid12_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid13" -> fingerprint.bssid13_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid14" -> fingerprint.bssid14_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid15" -> fingerprint.bssid15_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid16" -> fingerprint.bssid16_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid17" -> fingerprint.bssid17_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid18" -> fingerprint.bssid18_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid19" -> fingerprint.bssid19_rssi = convertDbToDbWatt(filteredResult.level)
                            "bssid20" -> fingerprint.bssid20_rssi = convertDbToDbWatt(filteredResult.level)


                            else -> {
                                print("BSSID Not existing")
                            }
                        }
                            fingerprint.label = "R${row}C${col}"




                        }
                    }
                    finger_db.fingerprintDao().insert(fingerprint)

                    callback.invoke("Sukses melakukan scanning")
                }else{
                    callback.invoke("Permission gagal")
                }


            } catch (e: Exception) {
                e.printStackTrace()
                callback.invoke("Terdapat Exception")
            }
        }


    }



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun performGlobalScan(db: BssidDatabase, context: Context ) {
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
                    println("test")
                    if(existingBSSID==null){

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

                print("hello")
            }
        } else {



        }
    }



}




fun exportDataToJson(db: FingerprintDatabase) {
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

            if (file.exists()) {
                file.delete()
            }

            FileWriter(file).use { writer ->
                writer.write(jsonData)
            }

            println("Export to JSON successful. File path: ${file.absolutePath}")
        } catch (e: Exception) {
            println("Export to JSON failed: ${e.message}")
        }
    }
}

fun dumpBssidDataToJson(db: BssidDatabase) {
    GlobalScope.launch(Dispatchers.IO) {
        val dao = db.bssidDao()
        val data = dao.getAll()

        val gson = Gson()
        val json = gson.toJson(data)

        val filename = "bssid.json"
        val downloadsFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadsFolder, filename)

        try {
            // Check if the file exists
            if (file.exists()) {
                // Delete the existing file
                file.delete()
            }

            // Create a new file
            FileOutputStream(file).use {
                it.write(json.toByteArray())
            }

            println("Dump BSSID data to JSON successful. File path: ${file.absolutePath}")
        } catch (e: IOException) {
            println("Dump BSSID data to JSON failed: ${e.message}")
        }
    }
}




fun convertDbToDbWatt(dBm: Int): Double {
    return 10.0.pow(dBm / 10.0)
}






