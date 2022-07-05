package com.example.mylibrary.model

import com.example.mylibrary.Resorces
import com.google.android.gms.maps.model.LatLng

class EventByUser(val appId:String, val resorcesId:String, val action:String, val userId:String,val meta: Meta) {
companion object{
    val TEST_APP_ID="ameer.hamza6733@gmail.com"
    val ACTION_VIEW="view"
    val TEST_USER_DEVICE_ID=""
}
}

class Meta(val timeStamp:Long,val location:LatLng){
   companion object{
       val TEST_GPS_LOCATION=LatLng(31.582045,74.329376)
   }
}