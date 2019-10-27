package org.mp.chiproject2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_landing_l.*
import org.mp.chiproject2.R
import org.mp.chiproject2.fragments.*

class LandingActivityL : AppCompatActivity() {

    val profileFragment = ProfileFragment()
    val mapFragment = MapFragment()
    val propFragment = PropertyListFragment()
    val tennantListFragment = TennantListFragment()

    val onNaviItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.nav_landlord_props -> {
                openFragment(propFragment)
                toolbar_title.setText("Properties")
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_tennant -> {
                openFragment(tennantListFragment)
                toolbar_title.setText("Tennants")
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_map -> {
                openFragment(mapFragment)
                toolbar_title.setText("Maps")
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_profile -> {
                openFragment(profileFragment)
                toolbar_title.setText("Profile")
                return@OnNavigationItemSelectedListener true
            }

        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_l)

        val bottomNav : BottomNavigationView = findViewById(R.id.navViewL)
        bottomNav.setOnNavigationItemSelectedListener(onNaviItemSelected)

        var mToolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        var actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, PropertyListFragment()).commit()

        mToolbar.setNavigationOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                supportFragmentManager.popBackStack()
            }
        })

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, fragment).addToBackStack(null).commit()
    }
}
