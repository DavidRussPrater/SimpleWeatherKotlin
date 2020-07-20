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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.android.simpleweather.R
import com.example.android.simpleweather.models.WeatherResponse
import com.example.android.simpleweather.network.WeatherService
import com.example.android.simpleweather.ui.main.SectionsPagerAdapter
import com.example.android.simpleweather.ui.main.SevenDayForecastFragment
import com.example.android.simpleweather.ui.main.TodaysForecastFragment
import com.example.android.simpleweather.ui.main.TomorrowsForecastFragment
import com.example.android.simpleweather.utils.Constants
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit.*


// OpenWeather Link : https://openweathermap.org/api

class MainActivity : AppCompatActivity() {


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
        val unitPreference: Boolean? = sharedPref.getBoolean("units_switch", false)
        Log.i("Preference", unitPreference.toString())

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
            Log.i("Swiped", "User Swiped")
            requestLocationData()
            swipeRefreshLayout.isRefreshing = false
        }

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
            Log.i("Current Latitude", "$latitude")

            val longitude = mLastLocation.longitude
            Log.i("Current Longitude", "$longitude")
            getLocationWeatherDetails(latitude, longitude, context = this@MainActivity)
        }
    }


    private fun getLocationWeatherDetails(latitude: Double, longitude: Double, context: Context){
        val unitConstant: String
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val unitPreference: Boolean = sharedPref.getBoolean("units_switch", false)

        if (unitPreference) {
            unitConstant = Constants.METRIC_UNIT
        } else {
            unitConstant = Constants.IMPERIAL_UNIT
        }

        if(Constants.isNetworkAvailable(this)) {

            val retrofit : Retrofit = Retrofit.Builder().
            baseUrl(Constants.BASE_URL).
            addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: WeatherService = retrofit
                .create<WeatherService>(WeatherService::class.java)

            val listCall: Call<WeatherResponse> = service.getWeather(
                latitude, longitude,
                unitConstant,
                Constants.EXCLUDE_MINUTELY,
                Constants.APP_ID
            )

            showCustomProgressDialog()

            listCall.enqueue(object : Callback<WeatherResponse>{
                override fun onFailure(t: Throwable) {
                    Log.e("Response Error", t.message.toString())
                    hideProgressDialog()
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onResponse(response: Response<WeatherResponse>?, retrofit: Retrofit?) {
                    if(response!!.isSuccess) {
                        hideProgressDialog()
                        val weatherList: WeatherResponse = response.body()
                        setupUI(weatherList)
                        Log.i("Response Result", "$weatherList")
                    } else {

                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            } else -> {
                            Log.e("Error", "Generic Error")
                        }

                        }
                    }
                }

            })

        } else {
            Toast.makeText(this@MainActivity,
                "No Internet Connection Available.",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun showCustomProgressDialog(){
        mProgressDialog = Dialog(this)
        /* Set the screen content from the layout resource folder
        * The resource will be inflated, adding all top-level view to the screen
        * */
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it
        mProgressDialog!!.show()
    }

    private fun hideProgressDialog() {
        if(mProgressDialog != null) {
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
            R.id.action_settings ->  {
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

    fun getUnitPreference(unitPreference: Boolean): Boolean{
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun setupUI(weatherList: WeatherResponse){

        supportFragmentManager.fragments.forEach {
            when (it) {
                is TodaysForecastFragment -> {
                    it.render(weatherList)
                }
                is TomorrowsForecastFragment -> {
                    it.render(weatherList)
                }
                is SevenDayForecastFragment -> {
                    it.render(weatherList)
                }
            }
        }

    }

}