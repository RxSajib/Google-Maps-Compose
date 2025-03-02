package com.news.mapscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.news.mapscompose.ui.theme.MapsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapsComposeTheme {

                Maps()
            }
        }
    }
}


@Composable
fun Maps(modifier: Modifier = Modifier) {

    val liverpoolPosition = LatLng(53.2422, -2.7650)
    val cameraposition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(liverpoolPosition, 10f)
    }

    val uisetting = MapUiSettings(  //todo ui for map kits
        compassEnabled = true,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true,
        tiltGesturesEnabled = true,
        zoomControlsEnabled = true
    )

    val mapProperties = MapProperties( //todo zoom map propertices
        maxZoomPreference = 18f,
        minZoomPreference = 5f,
        isTrafficEnabled = true,
        isBuildingEnabled = true,
        latLngBoundsForCameraTarget = LatLngBounds( //todo maps range
            LatLng(53.2017,-3.0257),
            LatLng(53.2840,-2.5325)
        ),
        mapType = MapType.HYBRID,
        isIndoorEnabled = true
    )


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraposition,
        properties = mapProperties,
        uiSettings = uisetting
    ){

    }
}