package org.mp.chiproject2.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_landing_l.*
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
                toolbar_title.setText("Properties")
                supportFragmentManager.popBackStack()
                openFragment(propFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_tennant -> {
                toolbar_title.setText("Tennants")
                supportFragmentManager.popBackStack()
                openFragment(tennantListFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_map -> {
                toolbar_title.setText("Maps")
                supportFragmentManager.popBackStack()
                openFragment(mapFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_landlord_profile -> {
                toolbar_title.setText("Profile")
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

        val bottomNav : BottomNavigationView = findViewById(R.id.navViewL)
        bottomNav.setOnNavigationItemSelectedListener(onNaviItemSelected2)

        toolbar_title.setText("Properties")
        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, PropertyListL()).commit()

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.main_frameL, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
