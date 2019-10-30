package org.mp.chiproject2.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_landing_t.*
import org.mp.chiproject2.R
import org.mp.chiproject2.views.fragments.*

class LandingActivityT : AppCompatActivity() {


    private val profileFragment = ProfileFragment()
    private val mapFragment = MapFragment()
    private val propFragment = PropertyListT()

    val onNaviItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.nav_tennant_props -> {
                toolbar_title.setText("Properties")
                supportFragmentManager.popBackStack()
                openFragment(propFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_map -> {
                toolbar_title.setText("Maps")
                supportFragmentManager.popBackStack()
                openFragment(mapFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_profile -> {
                toolbar_title.setText("Profiles")
                supportFragmentManager.popBackStack()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }

        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_t)

        val bottomNav : BottomNavigationView = findViewById(R.id.navViewT)
        bottomNav.setOnNavigationItemSelectedListener(onNaviItemSelected)

        toolbar_title.setText("Properties")
        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, PropertyListT()).commit()

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}
