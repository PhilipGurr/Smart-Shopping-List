package com.philipgurr.domain

import java.io.Serializable
import java.util.*


data class Product(
    val name: String = "",
    val created: Date = Date(),
    val completed: Boolean = false
) : Serializable
