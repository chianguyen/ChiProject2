package org.mp.chiproject2.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import org.mp.chiproject2.tools.LoginListener

class LoginViewModel: ViewModel() {

    var loginEmail: String? = null
    var loginPassword: String? = null

    var loginListener: LoginListener? = null

    fun onLoginBtnClick(view: View){




    }

}