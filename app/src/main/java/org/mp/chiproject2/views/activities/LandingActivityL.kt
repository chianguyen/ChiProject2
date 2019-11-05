package org.mp.chiproject2.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.fabric.sdk.android.Fabric
import org.mp.chiproject2.R
import org.mp.chiproject2.views.fragments.*

class LandingActivityL : AppCompatActivity() {

    private val profileFragment = ProfileFragment()
    private val mapFragment = MapFragment()
    private val propFragment = PropertyListL()
    private val tennantListFragment = TennantListFragment()

    val onNaviItemSelected2 = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.nav_landlord_props -> {
                supportFragmentManager.popBackStack()
                openFragment(propFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_tennant -> {
                supportFragmentManager.popBackStack()
                openFragment(tennantListFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_map -> {
                supportFragmentManager.popBackStack()
                openFragment(mapFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_profile -> {
                supportFragmentManager.popBackStack()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }

        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_l)

        Fabric.with(this, Crashlytics())

        val bottomNav : BottomNavigationView = findViewById(R.id.navViewL)
        bottomNav.setOnNavigationItemSelectedListener(onNaviItemSelected2)

        //toolbar_title.setText("Properties")

        var mToolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)


        var actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.setTitle("PROPERTITAN")


        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, PropertyListL()).commit()

        mToolbar.title = "PROPERTITAN"

        mToolbar.setNavigationOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                supportFragmentManager.popBackStack()
            }
        })

    }


    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
