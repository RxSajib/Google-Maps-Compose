package com.news.mapscompose

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.StyleSpan
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.news.mapscompose.ui.theme.MapsComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapsComposeTheme {

                Home()
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(modifier: Modifier = Modifier) {

    val context = LocalContext.current



    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            val locationLondon = LatLng(
                51.5072,
                -0.1276
            )

            val cameraposition = rememberCameraPositionState{
                this.position = CameraPosition.fromLatLngZoom(
                    locationLondon, 13f
                )
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraposition
            ){
                val polylinePoints = listOf(
                    LatLng(51.5007, -0.1246),  // Westminster
                    LatLng(51.5072, -0.1276),  // London Center
                    LatLng(51.5155, -0.1419),  // Oxford Street
                    LatLng(51.5200, -0.1050)   // Liverpool Street
                )
                val holePolygon = listOf(
                    LatLng(51.512, -0.14),  // Top-left
                    LatLng(51.512, -0.11),  // Top-right
                    LatLng(51.508, -0.11),  // Bottom-right
                    LatLng(51.508, -0.14),  // Bottom-left
                    LatLng(51.512, -0.14)   // Closing the hole
                )

                val poliLineColor = listOf(
                    Color.Blue,
                    Color.Red,
                    Color.Cyan
                )

               /* Polyline(
                    points = polylinePoints,
                    width = 15f,
                   // color = Color.Red
                    spans = poliLineColor.map {
                        StyleSpan(it.toArgb())
                    }
                )*/

                Polygon(
                    points = polylinePoints,
                    strokeWidth = 10f,
                    strokeColor = Color.Red,
                    holes = listOf(holePolygon)
                )
            }
        }
    }

}