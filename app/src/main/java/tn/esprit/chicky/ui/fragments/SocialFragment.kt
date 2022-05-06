

package tn.esprit.chicky.ui.fragments

import android.Manifest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.content.res.AppCompatResources

import androidx.fragment.app.Fragment

import com.mapbox.maps.MapView
import com.mapbox.maps.Style

import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D

import com.mapbox.maps.plugin.locationcomponent.location
import tn.esprit.chicky.R


class SocialFragment :   Fragment() {

     var mapView: MapView? = null
    /*
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
    private lateinit var mapView: MapView
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater.inflate(R.layout.fragment_social, container, false)

        // TODO
        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)



        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    mapView!!.location.locationPuck = LocationPuck2D(
                        topImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_icon
                        ),
                        bearingImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon
                        ),
                        shadowImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_stroke_icon
                        ),
                        scaleExpression = interpolate {
                            linear()
                            zoom()
                            stop {
                                literal(0.0)
                                literal(0.6)
                            }
                            stop {
                                literal(20.0)
                                literal(1.0)
                            }
                        }.toJson()
                    )
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    mapView!!.location.locationPuck = LocationPuck2D(
                        topImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_icon
                        ),
                        bearingImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon
                        ),
                        shadowImage = AppCompatResources.getDrawable(
                            view.context,
                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_stroke_icon
                        ),
                        scaleExpression = interpolate {
                            linear()
                            zoom()
                            stop {
                                literal(0.0)
                                literal(0.6)
                            }
                            stop {
                                literal(20.0)
                                literal(1.0)
                            }
                        }.toJson()
                    )
                } else -> {
                // No location access granted.
            }
            }
        }


































        /*
private fun initLocationComponent() {
    val locationComponentPlugin = mapView.location
    locationComponentPlugin.updateSettings {
        this.enabled = true
        this.locationPuck = LocationPuck2D(
            bearingImage = AppCompatResources.getDrawable(
                this@LocationTrackingActivity,
                R.drawable.mapbox_user_puck_icon,
            ),
            shadowImage = AppCompatResources.getDrawable(
                this@LocationTrackingActivity,
                R.drawable.mapbox_user_icon_shadow,
            ),
            scaleExpression = interpolate {
                linear()
                zoom()
                stop {
                    literal(0.0)
                    literal(0.6)
                }
                stop {
                    literal(20.0)
                    literal(1.0)
                }
            }.toJson()
        )
    }
    locationComponentPlugin.addOnIndicatorPositionChangedListener(
        onIndicatorPositionChangedListener
    )
    locationComponentPlugin.addOnIndicatorBearingChangedListener(
        onIndicatorBearingChangedListener
    )
}

        private fun onCameraTrackingDismissed() {
            Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
            mapView.location
                .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            mapView.location
                .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
            mapView.gestures.removeOnMoveListener(onMoveListener)
        }

        override fun onDestroy() {
            super.onDestroy()
            mapView.location
                .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
            mapView.location
                .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            mapView.gestures.removeOnMoveListener(onMoveListener)
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        private fun setupGesturesListener() {
            mapView.gestures.addOnMoveListener(onMoveListener)
        }




        private fun onMapReady() {
    mapView.getMapboxMap().setCamera(
        CameraOptions.Builder()
            .zoom(14.0)
            .build()
    )
    mapView.getMapboxMap().loadStyleUri(
        Style.MAPBOX_STREETS
    ) {
        initLocationComponent()
        setupGesturesListener()
    }
}




        super.onCreate(savedInstanceState)
        mapView = MapView(this)
        setContentView(mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
*/


    //  val locationPermissionRequest = registerForActivityResult(








        return view
    }


}
