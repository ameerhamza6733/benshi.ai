package com.example.example

import com.google.gson.annotations.SerializedName


data class UserReponse(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("address") var address: Address? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("website") var website: String? = null,
    @SerializedName("company") var company: Company? = null,
    var currentPositionInRecyclerView: Int = -1


)