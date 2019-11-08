package org.mp.chiproject2.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_register, container, false)

        view.btn_terms.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.beginTransaction().replace(R.id.login_act, Terms()).addToBackStack(null).commit()
            }
        })

        view.btn_back_to_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.popBackStack()
            }
        })

        view.reg_btn_reg.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var email = reg_edit_email.text.toString()
                var lemail = "x"
                var password = reg_edit_pwd.text.toString()
                var acctype = ""

                //validates email
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    reg_edit_email.error = "Enter valid email"
                }
                else {
                    reg_edit_email.error = null
                        //then validates password
                        if (password.isEmpty()) {
                            reg_edit_pwd.error = "Enter password"
                        } else {
                            reg_edit_pwd.error = null

                                if(view.reg_checkbox.isChecked){

                                    if(radio_landlord.isChecked) {
                                        acctype = "landlord"
                                    }
                                    else
                                        acctype = "tenant"

                                    userRegister(email, lemail, password, acctype)
                                    Log.i("ACCTYPE", acctype)
                                    Toast.makeText(view.context, "Registration successful!", Toast.LENGTH_SHORT).show()

                                } else{
                                    Toast.makeText(view.context, "You have to agree with our terms to register", Toast.LENGTH_LONG).show()
                                }

                        }

                }
            }
        })

        return view
    }

    fun userRegister(email: String, lemail: String, password: String, acctype: String) {
        var apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)
        var userCall = apiInterface.userReg(email, lemail, password, acctype)

        userCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("REG FAILURE", t.message.toString())
                Toast.makeText(view!!.context, "Register Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("REG RESPONSE", response.body()!!.string())
                Toast.makeText(view!!.context, "Registration Succcessful!", Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, LoginFragment()).addToBackStack(null).commit()
            }
        })

    }


}
