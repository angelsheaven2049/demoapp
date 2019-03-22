package com.angelsheaven.teremdemoapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.angelsheaven.teremdemoapp.R
import com.angelsheaven.teremdemoapp.utilities.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView


class MainActivity : AppCompatActivity(),MyLogger {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val mViewModel by lazy {
        //Inject view model to main activity
        val factory: MainActivityViewModelFactory? =
            provideMainActivityViewModelFactory(this.applicationContext, isItInitializedData(this))

        ViewModelProviders.of(this, factory)
            .get(MainActivityViewModel::class.java)
    }
    private var isNetworkConnected = false

    private val netWorkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {

                isNetworkConnected = isNetworkConnected(this)

                if (!isNetworkConnected) {
                    this@MainActivity.contentView?.let {
                        it.mySnackBar(
                            R.string.lost_connection_notification
                            , R.color.snackbar_background_color
                        ).show()
                    }
                }
            }

        }
    }
    private lateinit var mNotifyManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.networkConnectionState.observe(this, Observer { isNetworkConnected ->
            if (!isNetworkConnected) {
                this@MainActivity.contentView?.let {
                    it.mySnackBar(
                        R.string.check_network_connection
                        , R.color.snackbar_background_color
                    ).show()
                }
            }
        })

        //Setup navigation host fragment to control all fragment
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment

        val navController = host.navController

        //Setup action bar for activity
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.list_news_dest
                , R.id.list_bookmark_news_dest
                , R.id.login_dest
            ), drawer_layout
        )

        //setup action bar
        toolbar_layout.setupWithNavController(
            toolbar
            , navController, appBarConfiguration
        )

        //setup navigation menu
        nav_view.setupWithNavController(navController)

        //setup bottom bar
        bottom_nav_view.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.list_news_dest -> {
                    bottom_nav_view.visibility = View.VISIBLE
                    enableToolbarScrolling(true)
                    toolbar_layout.visibility = View.VISIBLE
                }
                R.id.list_bookmark_news_dest -> {
                    bottom_nav_view.visibility = View.VISIBLE
                    enableToolbarScrolling(true)
                    toolbar_layout.visibility = View.VISIBLE
                }
                R.id.view_news_detail_dest -> {
                    bottom_nav_view.visibility = View.GONE
                    enableToolbarScrolling(false)
                    toolbar_layout.visibility = View.VISIBLE
                }
                R.id.login_dest -> {
                    toolbar_layout.visibility = View.GONE
                    bottom_nav_view.visibility = View.GONE
                }
            }

        }

        createNotificationChannel()

        if (!isItInitializedData(this)) {
            mViewModel.updateInitializeDataState.observeOnce(this
                , Observer { isUpdated ->
                    if (isUpdated) {
                        this.getMyPreferences()?.edit {
                            log("Update initialize data flag")
                            putBoolean(INITIALIZE_DATA, true)
                        }
                    }
                })
        }

    }

    private fun enableToolbarScrolling(enable: Boolean) {
        val params = toolbar_layout.layoutParams
                as AppBarLayout.LayoutParams
        if (enable) {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        } else {
            params.scrollFlags = 0
        }
    }

    override fun onResume() {
        super.onResume()

        //Register network connectivity change listener
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netWorkChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(netWorkChangeReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        if (nav_view == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }

        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item?.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment)
            .navigateUp(appBarConfiguration)
    }

    private fun createNotificationChannel() {
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (android.os.Build.VERSION.SDK_INT
            >= android.os.Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID
                , NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = NOTIFICATION_DESCRIPTION
            }

            mNotifyManager.createNotificationChannel(notificationChannel)

        }
    }


}
