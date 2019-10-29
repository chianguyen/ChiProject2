package org.mp.chiproject2.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONObject

import org.mp.chiproject2.R
import org.mp.chiproject2.activities.LandingActivityL
import org.mp.chiproject2.activities.LandingActivityT
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        //-------Buttons-----------------------------------------------------------------
        view.login_btn_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var loginEmail = login_edit_email.text.toString()
                var loginPwd = login_edit_password.text.toString()

                var sp_email = sp.getString("user_email", "")
                var sp_pwd = sp.getString("user_pwd", "")

                view.login_edit_email.setText(sp_email)
                view.login_edit_password.setText(sp_pwd)

                if(loginEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()){
                    login_edit_email.setError("Enter valid email")
                }
                else {
                    login_edit_email.setError(null)

                    //Call login using retrofit
                    userLogin(loginEmail, loginPwd)

                }

            }
        })

        view.login_btn_register.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var regFrag = RegisterFragment()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, regFrag).addToBackStack(null).commit()
            }
        })

        view.login_btn_forgot.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var forgotFrag = ForgotPwdFragment()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, forgotFrag).addToBackStack(null).commit()
            }
        })

        return view
    }

    fun userLogin(email: String, password: String){

        var apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userCall = apiInterface.userLogin(email, password)

        userCall.enqueue(object : retrofit2.Callback<JsonObject> {

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("LOGIN RESPONSE", t.message.toString())
                Toast.makeText(view!!.context, "Email is not registered", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var userLogin = response.body()
                Log.i("USER OBJECT", response.body().toString())
                Log.i("USER MSG", userLogin!!.get("msg")!!.asString)

                var loginMsg = userLogin.get("msg")!!.asString

                var sp: SharedPreferences = view!!.context.getSharedPreferences("chamber", Context.MODE_PRIVATE)
                var editor = sp.edit()

                if(loginMsg == "success"){

                    var userId = userLogin.get("userid")!!.asString
                    var userType = userLogin.get("usertype")!!.asString
                    var userEmail = userLogin.get("useremail")!!.asString
                    var userApikey = userLogin.get("appapikey")!!.asString

                    Log.i("USER ID", userId)
                    Log.i("USER TYPE", userType)
                    Log.i("USER EMAIL", userEmail)
                    Log.i("USER APIKEY", userApikey)

                    editor.putString("user_id", userId)
                    editor.putString("user_email", userEmail)
                    editor.putString("user_apikey", userApikey)
                    editor.putString("user_pwd", password)

                    if(userType == "tenant"){
                        var ii = Intent(view!!.context, LandingActivityT::class.java)
                        startActivity(ii)
                    }

                    else {
                        var i = Intent(view!!.context, LandingActivityL::class.java)
                        startActivity(i)
                    }
                    editor.apply()
                }
                else{
                    Toast.makeText(view!!.context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
