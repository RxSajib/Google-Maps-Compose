package com.news.mapscompose

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.StyleSpan
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.clustering.Clustering
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


@OptIn(ExperimentalPermissionsApi::class, MapsComposeExperimentalApi::class)
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
               /* val marker = listOf(
                    LatLng(51.5007, -0.1246),  // Westminster
                    LatLng(51.5072, -0.1276),  // London Center
                    LatLng(51.5155, -0.1419),  // Oxford Street
                    LatLng(51.5200, -0.1050)   // Liverpool Street
                )*/

                val markerData = listOf(
                    MarkerDaata(
                        location =  LatLng(51.5007, -0.1246),
                        title = "This is marker 1",
                        details = "Details of marker 1",
                        image = R.drawable.ic_launcher_background
                    ),
                    MarkerDaata(
                        location =   LatLng(51.5072, -0.1276),
                        title = "This is marker 2",
                        details = "Details of marker 2",
                        image = R.drawable.ic_launcher_background
                    ),
                    MarkerDaata(
                        location =   LatLng(51.5155, -0.1419),
                        title = "This is marker 3",
                        details = "Details of marker 3",
                        image = R.drawable.ic_launcher_background
                    ),
                    MarkerDaata(
                        location =   LatLng(51.5200, -0.1050),
                        title = "This is marker 4",
                        details = "Details of marker 4",
                        image = R.drawable.ic_launcher_background
                    )
                )

                markerData.forEach{ marker ->
                    Marker(
                        state = MarkerState(marker.location),
                        title = marker.title,
                        contentDescription = marker.details
                    )

                    MarkerInfoWindowContent(
                        state = MarkerState(
                            position = marker.location
                        ),
                        title = marker.title,
                        snippet = marker.details
                    ){
                        Box(modifier = Modifier.padding(15.dp)){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(marker.image),
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp).clip(shape = CircleShape)
                                )
                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )
                                marker.title?.let {
                                    Text(
                                        text = it,
                                        style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                                    )
                                }
                                marker.details?.let {
                                    Text(
                                        text = it
                                    )
                                }


                            }

                        }
                    }

                    Clustering(
                        items = markerData,
                        onClusterClick = {
                            false
                        },
                        onClusterItemClick = {
                            false
                        }
                    )
                }


                /*marker.forEach {
                    Marker(
                        state = MarkerState(it)
                    )
                }*/
            }
        }
    }
}

data class MarkerDaata(
    val location : LatLng,
    @get:JvmName("getMarkerTitle") val title : String?,
    val details : String?,
    @DrawableRes val image : Int
) : ClusterItem{
    override fun getPosition(): LatLng {
        return location
    }

    override fun getTitle(): String? {
       return title
    }

    override fun getSnippet(): String? {
        return details
    }

    override fun getZIndex(): Float {
       return 1f
    }

}