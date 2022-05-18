package tn.esprit.chicky.ui.fragments

import android.Manifest
import android.app.Application
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.content.res.AppCompatResources

import androidx.fragment.app.Fragment
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment.TAG
import com.mapbox.maps.CameraOptions

import com.mapbox.maps.MapView
import com.mapbox.maps.Style

import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.gestures

import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.search.MapboxSearchSdk
import tn.esprit.chicky.R


class SocialFragment : Fragment() {

    var mapView: MapView? = null

    //get nearby places



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_social, container, false)



        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)






        MapboxSearchSdk.initialize(
            application = Application() ,
            accessToken = getString(R.string.mapbox_access_token),
            locationEngine = LocationEngineProvider.getBestLocationEngine(activity!!)
        )



        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                }
                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val locationManager = activity!!.getSystemService(LOCATION_SERVICE) as LocationManager

        try {
            // Request location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            onMapReady()
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }



        return view
    }

    private fun onMapReady() {
        mapView!!.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView!!.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
        }
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView!!.location
        println("-----------------------------------")
        println(mapView!!.location)
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    activity!!.applicationContext,
                    R.drawable.mapbox_user_puck_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    activity!!.applicationContext,
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
        //locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        //locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            println("" + location.longitude + ":" + location.latitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}

    }


}
