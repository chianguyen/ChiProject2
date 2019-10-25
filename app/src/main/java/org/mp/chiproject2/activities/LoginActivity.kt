package org.mp.chiproject2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.mp.chiproject2.R
import org.mp.chiproject2.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var loginFrag = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.login_act, loginFrag).commit()

    }
}
