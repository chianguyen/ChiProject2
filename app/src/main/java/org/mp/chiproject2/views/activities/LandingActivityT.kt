package org.mp.chiproject2.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_landing_t.*
import org.mp.chiproject2.R
import org.mp.chiproject2.views.fragments.*

class LandingActivityT : AppCompatActivity() {


    private val profileFragment = ProfileFragment()
    private val mapFragment = MapFragmentT()
    private val propFragment = PropertyListT()

    val onNaviItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.nav_tennant_props -> {
                toolbar_title.text = "Properties"
                supportFragmentManager.popBackStack()
                openFragment(propFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_map -> {
                toolbar_title.text = "Maps"
                supportFragmentManager.popBackStack()
                openFragment(mapFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tennant_profile -> {
                toolbar_title.text = "Profiles"
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

        Fabric.with(this, Crashlytics())

/*        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }*/

        val bottomNav : BottomNavigationView = findViewById(R.id.navViewT)
        bottomNav.setOnNavigationItemSelectedListener(onNaviItemSelected)

        var mToolbar : Toolbar = findViewById(R.id.toolbarT)
        setSupportActionBar(mToolbar)


        var actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.setTitle("PROPERTITAN")

        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, PropertyListT()).commit()



        mToolbar.setNavigationOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                supportFragmentManager.popBackStack()
            }
        })


    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.main_frameT, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}
