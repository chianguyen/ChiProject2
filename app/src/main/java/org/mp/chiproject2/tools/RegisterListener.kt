package org.mp.chiproject2.tools

interface RegisterListener {

    fun onEmailError(message: String)
    fun onPwdError(message: String)

}