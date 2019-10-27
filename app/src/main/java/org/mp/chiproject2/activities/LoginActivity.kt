package org.mp.chiproject2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.mp.chiproject2.R
import org.mp.chiproject2.fragments.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var loginFrag = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.login_act, loginFrag).commit()

    }

}
