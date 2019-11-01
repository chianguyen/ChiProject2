package org.mp.chiproject2.tools

interface LoginListener {

    fun onEmailError(message: String)
    fun onPwdError(message: String)
}