package org.mp.chiproject2.views.fragments


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
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import com.google.firebase.auth.FirebaseAuth

import org.mp.chiproject2.R
import org.mp.chiproject2.views.activities.LandingActivityL
import org.mp.chiproject2.views.activities.LandingActivityT
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.views.activities.ForgotPwdAct
import retrofit2.Call
import retrofit2.Response
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.mp.chiproject2.views.activities.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*


class LoginFragment : Fragment() {

    val GOOGLE_SIGN = 123
    var mAuth: FirebaseAuth? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_login, container, false)

        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        var sp: SharedPreferences    = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)
        var s_email = sp.getString("login_key", "")
        var s_pwd = sp.getString("login_pwd", "")

        view.login_edit_email.setText(s_email.toString())
        view.login_edit_password.setText(s_pwd.toString())

        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        view.login_btn_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var loginEmail = login_edit_email.text.toString()
                var loginPwd   = login_edit_password.text.toString()

                var editor = sp.edit()


                if(loginEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()){
                    login_edit_email.error = "Enter valid email"
                }
                else {
                    login_edit_email.error = null

                    if(view.login_checkbox.isChecked){
                        editor.putString("login_key", loginEmail)
                        editor.putString("login_pwd", loginPwd)

                        editor.commit()
                    }

                    //Call login using retrofit
                    userLogin(loginEmail, loginPwd)

                }
            }
        })

        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        view.text_register.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var regFrag    = RegisterFragment()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, regFrag).addToBackStack(null).commit()
            }
        })

        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        view.text_forgot.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var i = Intent(view!!.context, ForgotPwdAct::class.java)
                startActivity(i)
            }
        })

        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        mAuth = FirebaseAuth.getInstance()

        var googleSignInOptions = GoogleSignInOptions
            .Builder()
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.context!!, googleSignInOptions)

        view.sign_in_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signInGoogle()

                if (mAuth!!.currentUser != null){

                    var user: FirebaseUser = mAuth!!.currentUser!!

                    updateUI(user)

                    var editor = sp.edit()
                    editor.putString("user_email", user.email)
                    editor.commit()

                    val builder = AlertDialog.Builder(view.context)
                    builder.setTitle("Sign in with Google")
                    builder.setMessage("Do you want to login as tenant or landlord?")

                    builder.setPositiveButton("Tenant"){dialog, which ->


                        var ii = Intent(view!!.context, LandingActivityT::class.java)
                        startActivity(ii)
                    }

                    builder.setNegativeButton("Landlord"){dialog, which ->

                        var ii = Intent(view!!.context, LandingActivityL::class.java)
                        startActivity(ii)
                    }

                    // Finally, make the alert dialog using builder
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()

                }
            }
        })


        //=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        return view
    }

    fun signInGoogle(){
        val signIntent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signIntent, GOOGLE_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN){
            var task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {

                var account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null){
                    firebaseAuthWithGoogle(account)
                }

            }
            catch (e: ApiException){
                e.printStackTrace()
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("TAG", "firebaseAuthWthGoogle: " + account.id)

        var credentia: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth?.signInWithCredential(credentia)?.addOnCompleteListener(LoginActivity(), OnCompleteListener {
            var user : FirebaseUser = mAuth!!.currentUser!!
            updateUI(user)
        })
    }

    private fun updateUI(user: FirebaseUser) {
        if(user != null){
            var name = user.displayName
            var email = user.email
        }
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

                    var userId      = userLogin.get("userid")!!.asString
                    var userType    = userLogin.get("usertype")!!.asString
                    var userEmail   = userLogin.get("useremail")!!.asString
                    var userApikey  = userLogin.get("appapikey")!!.asString

                    Log.i("USER ID", userId)
                    Log.i("USER TYPE", userType)
                    Log.i("USER EMAIL", userEmail)
                    Log.i("USER APIKEY", userApikey)

                    editor.putString("user_id", userId)
                    editor.putString("user_type", userType)
                    editor.putString("user_email", userEmail)
                    editor.putString("user_apikey", userApikey)
                    editor.putString("user_pwd", password)

                    if(userType == "tenant"){
                        var ii = Intent(view!!.context, LandingActivityT::class.java)
                        startActivity(ii)
                    }

                    if (userType == "landlord") {
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
