package com.fireless.firecheck

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fireless.firecheck.databinding.ActivityMainBinding
import com.fireless.firecheck.network.FirebaseDBMng
import com.fireless.firecheck.network.FirebaseUserLiveData
import com.fireless.firecheck.ui.company.NewCompanyFragmentDirections
import com.fireless.firecheck.ui.extinguisher.NewExtinguisherFragmentDirections
import com.fireless.firecheck.ui.login.LoginActivity
import com.fireless.firecheck.ui.maintenance.NewMaintenanceFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.materialstudies.reply.util.contentView


class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //observe if a user is logged or not
        FirebaseUserLiveData().observe(this, { authenticationState ->
            if (authenticationState == null) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                // initialization of user info (Firebase realtime database)
                FirebaseDBMng.initFirebaseDB()
                setUpFab()
                //setUpBottomNavigationAndFab()
            }
        })
    }

    private fun setUpFab() {
        binding.fab.apply {


            addActionItem(SpeedDialActionItem.Builder(
                R.id.fab_maintenance,
                R.drawable.ic_baseline_playlist_add_24)
                .setLabelColor(Color.WHITE)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue_600, theme))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setLabel("New maintenance")
                .setLabelClickable(false)
                .create())

            addActionItem(SpeedDialActionItem.Builder(
                R.id.fab_extinguisher,
                R.drawable.ic_baseline_fire_extinguisher_24)
                .setLabelColor(Color.WHITE)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue_600, theme))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setLabel("New extinguisher")
                .setLabelClickable(false)
                .create())

            addActionItem(SpeedDialActionItem.Builder(
                R.id.fab_company,
                R.drawable.ic_baseline_add_business_24)
                .setLabelColor(Color.WHITE)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue_600, theme))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setLabel("New company")
                .setLabelClickable(false)
                .create())


            setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
                when (actionItem.id) {
                    R.id.fab_maintenance -> {
                        navigateToNewMaintenance()
                        close() // To close the Speed Dial with animation
                        return@OnActionSelectedListener true // false will close it without animation
                    }
                    R.id.fab_extinguisher -> {
                        navigateToNewExtinguisher()
                        close()
                        return@OnActionSelectedListener true
                    }
                    R.id.fab_company -> {
                        navigateToNewCompany()
                        close()
                        return@OnActionSelectedListener false
                    }
                }
                true // To keep the Speed Dial open
            })

        }
    }

    private fun navigateToNewMaintenance() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        val directions = NewMaintenanceFragmentDirections.actionGlobalNewMaintenanceFragment()
        findNavController(R.id.nav_host_fragment).navigate(directions)
    }

    private fun navigateToNewExtinguisher() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        val directions = NewExtinguisherFragmentDirections.actionGlobalNewExtinguisherFragment()
        findNavController(R.id.nav_host_fragment).navigate(directions)
    }

    private fun navigateToNewCompany() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        val directions = NewCompanyFragmentDirections.actionGlobalNewCompanyFragment()
        findNavController(R.id.nav_host_fragment).navigate(directions)
    }

    /*
    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

     */

}