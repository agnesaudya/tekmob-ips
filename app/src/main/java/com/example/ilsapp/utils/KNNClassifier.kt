package com.example.ilsapp.utils

import kotlin.math.pow
import kotlin.math.sqrt

class KNNClassifier {

    fun classify(
        data: List<HashMap<String, List<Int>>>,
        labels: List<Int>,
        query: List<Int>,
        k: Int
    ): Int {
        val distances = mutableListOf<Pair<Int, Double>>()
        for (i in data.indices) {
            val distance = euclideanDistance(data[i], query)
            distances.add(Pair(i, distance))
        }
        distances.sortBy { it.second }
        val kNearest = distances.subList(0, k) // k nearest neighbors
        val kNearestLabels = kNearest.map { labels[it.first] } // it.first is the index of the data point
        return kNearestLabels.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: -1
    }

    private fun euclideanDistance(a: List<Int>, b: List<Int>): Double {
        var sum = 0.0
        for (i in a.indices) {
            sum += (a[i] - b[i]).toDouble().pow(2.0)
        }
        return sqrt(sum)
    }
}