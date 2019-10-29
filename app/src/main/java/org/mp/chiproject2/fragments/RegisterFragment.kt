package org.mp.chiproject2.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
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

        view.reg_btn_reg.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var email = reg_edit_email.text.toString()
                var lemail = reg_edit_landlord_email.text.toString()
                var password = reg_edit_pwd.text.toString()
                var acctype = reg_edit_account_type.text.toString()

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    reg_edit_email.setError("Enter valid email")
                }
                else {
                    reg_edit_email.setError(null)

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(lemail).matches()) {
                        reg_edit_landlord_email.setError("Enter valid landlord email")
                    } else {
                        reg_edit_landlord_email.setError(null)

                        if (password.isEmpty()) {
                            reg_edit_pwd.setError("Enter password")
                        } else {
                            reg_edit_pwd.setError(null)

                            if (acctype == "tenant" || acctype == "landlord") {
                                reg_edit_account_type.setError(null)

                                userRegister(email, lemail, password, acctype)

                            } else {
                                reg_edit_account_type.setError("Enter valid account type")
                            }
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
                Toast.makeText(view!!.context, response.body()!!.string(), Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, LoginFragment()).addToBackStack(null).commit()
            }
        })

    }


}
