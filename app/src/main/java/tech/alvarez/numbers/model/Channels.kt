package tech.alvarez.numbers.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Channels(
    @SerializedName("items")
    private var items: ArrayList<Item?>? = null
)