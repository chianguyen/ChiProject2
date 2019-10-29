package org.mp.chiproject2.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_landing_t.*
import org.mp.chiproject2.R
import org.mp.chiproject2.fragments.*

class LandingActivityT : AppCompatActivity() {


    private val profileFragment = ProfileFragment()
    private val mapFragment = MapFragment()
    private val propFragment = PropertyListT()

    val onNaviItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.nav_tennant_props -> {
                supportFragmentManager!!.popBackStack()
                toolbar_title.setText("Properties")
                openFragment(propFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_map -> {
                supportFragmentManager!!.popBackStack()
                openFragment(mapFragment)
                toolbar_title.setText("Maps")
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_profile -> {
                supportFragmentManager!!.popBackStack()
                openFragment(profileFragment)
                toolbar_title.setText("Profiles")
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

        var sp: SharedPreferences = this.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        toolbar_title.setText("Properties")
        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, PropertyListFragment()).commit()

    }

    private fun openFragment(fragment: Fragment){
//        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}
