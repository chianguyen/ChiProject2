package org.mp.chiproject2.views.activities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_forgot_pwd.*
import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class ForgotPwdAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pwd)

        forgot_btn_forgot.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var forgotEmail = forgot_edit_email.text.toString()

                if(forgotEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(forgotEmail).matches()){
                    forgot_edit_email.setError("Enter valid email")
                }
                else {
                    forgot_edit_email.setError(null)

                    retrievePwd(forgotEmail)

                }

            }
        })

        forgot_btn_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var i = Intent(this@ForgotPwdAct, LoginActivity::class.java)
                startActivity(i)
            }
        })

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

    }

    fun retrievePwd(email: String){
        val apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)
        val userCall = apiInterface.retrievePwd(email)

        userCall.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.i("RETRIEVE ERROR", t.message.toString())
                Toast.makeText(this@ForgotPwdAct, "FAILED", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var userRetr = response.body()
                var forgotPwd = userRetr!!.get("userpassword").asString

                Toast.makeText(this@ForgotPwdAct, "Please check your notification for password", Toast.LENGTH_LONG).show()

                //Notification
                var channelName = NotificationChannel("channelID01", "channelName01", NotificationManager.IMPORTANCE_DEFAULT)
                channelName.description = "myDescription"

                var notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notiManager.createNotificationChannel(channelName)

                var notifcationBuilder = Notification.Builder(this@ForgotPwdAct, channelName.id)

                notifcationBuilder.setChannelId(channelName.id)
                notifcationBuilder.setContentTitle("Forgot Password?")
                notifcationBuilder.setContentText("Your password is: $forgotPwd")
                notifcationBuilder.setSmallIcon(R.drawable.ic_launcher_background)

                notifcationBuilder.setAutoCancel(true)
                //Notification service
                notiManager.notify(12, notifcationBuilder.build()) //any number for id


            }
        })

    }
}