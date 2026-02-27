package com.example.resourcehub

import java.io.Serializable

data class Resource(
    val name: String,
    val address: String,
    val phone: String,
    val hours: String,
    val category: String
) : Serializable
