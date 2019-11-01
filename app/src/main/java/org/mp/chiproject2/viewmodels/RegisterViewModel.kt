package org.mp.chiproject2.viewmodels

import androidx.lifecycle.ViewModel
import org.mp.chiproject2.tools.RegisterListener

class RegisterViewModel: ViewModel() {

    var regEmail: String? = null
    var regLEmail: String? = null
    var regPwd: String? = null

    var regiterListener: RegisterListener? = null




}