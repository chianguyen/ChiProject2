package org.mp.chiproject2.views.fragments


import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_forgot_pwd.*
import kotlinx.android.synthetic.main.fragment_forgot_pwd.view.*
import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.views.activities.LoginActivity
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ForgotPwdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_forgot_pwd, container, false)

        view.forgot_btn_forgot.setOnClickListener(object : View.OnClickListener{
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

        view.forgot_btn_login.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.popBackStack()
            }
        })
        return view
    }

    fun retrievePwd(email: String){
        val apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)
        val userCall = apiInterface.retrievePwd(email)

        userCall.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.i("RETRIEVE ERROR", t.message.toString())
                Toast.makeText(view!!.context, "FAILED", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var userRetr = response.body()
                var forgotPwd = userRetr!!.get("userpassword").asString

                Toast.makeText(view!!.context, "Your password is: " + forgotPwd, Toast.LENGTH_LONG).show()

            }
        })

    }


}
