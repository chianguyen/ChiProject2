package org.mp.chiproject2.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.mp.chiproject2.R
import org.mp.chiproject2.views.fragments.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Fabric.with(this, Crashlytics())

        supportFragmentManager.beginTransaction().replace(R.id.login_act, LoginFragment()).commit()

        login_act.animation = AnimationUtils.loadAnimation(this, R.anim.fate_transistion_anim2)

    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

}
