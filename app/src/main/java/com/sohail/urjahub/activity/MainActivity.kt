package com.sohail.urjahub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sohail.urjahub.R
import com.sohail.urjahub.fragments.*
import com.sohail.urjahub.fragments.Map

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frame: FrameLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.CoordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frame = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        openHome()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.Dashboard_home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.Dashboard_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, Profile()).commit()
                    supportActionBar?.title = getString(R.string.my_profile)
                    drawerLayout.closeDrawers()
                }
                R.id.Dashboard_faq -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, Faq()).commit()
                    supportActionBar?.title = getString(R.string.faqs)
                    drawerLayout.closeDrawers()
                }
                R.id.Dashboard_settings -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, Settings()).commit()
                    supportActionBar?.title = getString(R.string.settings)
                    drawerLayout.closeDrawers()
                }
                R.id.Dashboard_trip_history -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, TripHistory()).commit()
                    supportActionBar?.title = getString(R.string.trips)
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    private fun openHome() {
        val fragment = Map()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = getString(R.string.home)
        navigationView.setCheckedItem(R.id.Dashboard_home)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            !is Map -> openHome()
            else -> super.onBackPressed()

        }

    }
}