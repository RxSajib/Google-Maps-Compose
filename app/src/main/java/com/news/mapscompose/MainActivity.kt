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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
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

    val liverpoolPosition = LatLng(53.2422, -2.7650)
    val cameraposition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(liverpoolPosition, 10f)
    }
    val locationPermission = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val scope = rememberCoroutineScope()
    //todo location provider
    // Location provider
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(locationPermission.allPermissionsGranted) {
        if (locationPermission.allPermissionsGranted) {
            // Get current location if permission granted
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                locationPermission.launchMultiplePermissionRequest()
                return@LaunchedEffect
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Update the state with the current location
                    currentLocation = it
                    // Update camera position
                    scope.launch {
                        cameraposition.animate(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f))
                    }
                }
            }
        }
    }
    //todo location provider


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(locationPermission.allPermissionsGranted){
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let {

                            currentLocation = it
                            scope.launch {
                                cameraposition.animate(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f))
                            }
                        }
                    }
                }else {
                    locationPermission.launchMultiplePermissionRequest()
                }
            }, shape = CircleShape,
                containerColor = Color.White,
                contentColor = if(locationPermission.allPermissionsGranted) {
                    Color.Blue
                }else {
                    Color.Black
                }
                ) {
                Icon(
                    painter = painterResource(R.drawable.location_crosshairs_svgrepo_com),
                    contentDescription = null
                )
            }
        }
    ) { innderPadding ->
        Box(modifier = Modifier.padding(innderPadding)){



            val uisetting = MapUiSettings(  //todo ui for map kits
                compassEnabled = true,
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = true,
                tiltGesturesEnabled = true,
                zoomControlsEnabled = false
            )

            val mapProperties = MapProperties( //todo zoom map propertices
                maxZoomPreference = 18f,
                minZoomPreference = 5f,
                isTrafficEnabled = true,
                isBuildingEnabled = true,
              /*  latLngBoundsForCameraTarget = LatLngBounds( //todo maps range
                    LatLng(53.2017,-3.0257),
                    LatLng(53.2840,-2.5325)
                ),*/
                mapType = MapType.NORMAL,
                isIndoorEnabled = true,
                isMyLocationEnabled = true //todo my location enable
            )


            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraposition,
                properties = mapProperties,
                uiSettings = uisetting
            ){

            }


        }
    }



}