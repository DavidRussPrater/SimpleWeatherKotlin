package com.example.android.simpleweather.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.android.simpleweather.R
import com.example.android.simpleweather.data.model.WeatherResponse
import com.example.android.simpleweather.persistence.model.Current
import com.example.android.simpleweather.persistence.model.CurrentAndToday
import com.example.android.simpleweather.ui.main.SectionsPagerAdapter
import com.example.android.simpleweather.ui.main.sevenday.SevenDayForecastFragment
import com.example.android.simpleweather.ui.main.today.TodayViewModel
import com.example.android.simpleweather.ui.main.today.TodaysForecastFragment
import com.example.android.simpleweather.ui.main.tomorrow.TomorrowsForecastFragment
import com.example.android.simpleweather.utils.Constants
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


// OpenWeather Link : https://openweathermap.org/api

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TodayViewModel>()

    // A fused location client variable which is further used to get the user's current location
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var mProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById<ViewPager>(R.id.view_pager).apply {
            offscreenPageLimit = 2
        }
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.getBoolean("units_switch", false)

        // Initialize the Fused location variable
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (!isLocationEnabled()) {
            Toast.makeText(
                this,
                "Your location provider is turned off. Please turn it on.",
                Toast.LENGTH_SHORT
            ).show()

            // This will redirect you to settings from where you need to turn on the location provider.
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {

            Dexter.withActivity(this)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            requestLocationData()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                this@MainActivity,
                                "You have denied location permission. Please allow it is mandatory.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()

        }

        swipeRefreshLayout.setOnRefreshListener {
            requestLocationData()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.insertCurrent(37.6439, -98.7376)
        Thread.sleep(3000)

        viewModel.currentWeather.observe(this, { weather ->
            Timber.i("Here here")
            renderUI(weather)
        })

    }

    /**
     * A function which is used to verify that the location or GPS is enable or not of the user's device.
     */
    private fun isLocationEnabled(): Boolean {

        // This provides access to the system location services.
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    /**
     * A function used to show the alert dialog when the permissions are denied and need to allow it from settings app info.
     */
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog,
                                           _ ->
                dialog.dismiss()
            }.show()
    }


    /**
     * A function to request the current location. Using the fused location provider client.
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }


    /**
     * A location callback object of fused location provider client where we will get the current location details.
     */
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation

            val latitude = mLastLocation.latitude
            val longitude = mLastLocation.longitude
            getLocationWeatherDetails(latitude, longitude)
        }
    }


    private fun getLocationWeatherDetails(latitude: Double, longitude: Double) {

        if (Constants.isNetworkAvailable(this)) {
            showCustomProgressDialog()
            viewModel.insertCurrent(latitude, longitude)

        } else {
            Toast.makeText(
                this@MainActivity,
                "No Internet Connection Available.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun showCustomProgressDialog() {
        mProgressDialog = Dialog(this)
        /* Set the screen content from the layout resource folder
        * The resource will be inflated, adding all top-level view to the screen
        * */
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it
        mProgressDialog!!.show()
    }

    private fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                requestLocationData()
                true
            }
            R.id.action_settings -> {
                val settingsIntent = Intent(
                    this,
                    SettingsActivity::class.java
                )
                startActivity(settingsIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun renderUI(current: CurrentAndToday) {

        supportFragmentManager.fragments.forEach {
            when (it) {
                is TodaysForecastFragment -> {
                    it.render(current)
                }
//                is TomorrowsForecastFragment -> {
//                    it.render(weatherList)
//                }
//                is SevenDayForecastFragment -> {
//                    it.render(weatherList)
//                }
            }
        }
    }
}