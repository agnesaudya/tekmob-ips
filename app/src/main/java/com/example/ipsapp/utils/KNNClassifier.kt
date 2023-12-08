package com.example.ipsapp.utils


import com.example.ipsapp.entity.Fingerprint

import kotlin.math.pow
import kotlin.math.sqrt

class KNNClassifier {
//    constructur
    private var data: List<Fingerprint>
    constructor(fingerprint: List<Fingerprint>) {
        data = fingerprint

    }



     fun classify(
        query: List<Double>,
        k: Int
    ): String {
        for (i in data){
            val distance = euclideanDistance(i, query)


        }
        val labels = data.map { it.label }
        val distances = mutableListOf<Pair<Int, Double>>()
        for (i in data.indices) {
            val distance = euclideanDistance(data[i], query)
            distances.add(Pair(i, distance))
        }
        distances.sortBy { it.second }

        val kNearest = distances.subList(0, k) // k nearest neighbors
        val kNearestLabels = kNearest.map { labels[it.first] } // it.first is the index of the data point
//        count the number of each label
        val labelCounts = kNearestLabels.groupingBy { it }.eachCount()
        return labelCounts.maxBy { it.value }!!.key


    }

    private fun euclideanDistance(a: Fingerprint, b: List<Double>): Double {
        val list = listOf(
            a.bssid1_rssi,
            a.bssid2_rssi,
            a.bssid3_rssi,
            a.bssid4_rssi,
            a.bssid5_rssi,
            a.bssid6_rssi,
            a.bssid7_rssi,
            a.bssid8_rssi,
            a.bssid9_rssi,
            a.bssid10_rssi,
            a.bssid11_rssi,
            a.bssid12_rssi,
            a.bssid13_rssi,
            a.bssid14_rssi)
        var sum = 0.0
        for (i in list.indices) {
            sum += (list[i] - b[i]).pow(2.0)
        }
        return sqrt(sum)
    }
}