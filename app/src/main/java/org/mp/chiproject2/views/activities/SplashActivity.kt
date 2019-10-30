package org.mp.chiproject2.views.activities

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import org.mp.chiproject2.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var lottieAniView : LottieAnimationView = findViewById(R.id.lottieAnimationView2)
        var lottieAniView2 : LottieAnimationView = findViewById(R.id.lottieAnimationView3)

        lottieAniView.setRepeatCount(1)
        lottieAniView2.setRepeatCount(1)

        lottieAniView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                var i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })



    }
}
