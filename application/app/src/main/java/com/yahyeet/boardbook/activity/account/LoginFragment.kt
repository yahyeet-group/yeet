package com.yahyeet.boardbook.activity.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.yahyeet.boardbook.R

class LoginFragment : AccountFragment() {

    private var loginButton: Button? = null


    private var tvEmail: TextView? = null
    private var tvPassword: TextView? = null
    private var tvError: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentContainer = view
        return inflater.inflate(R.layout.fragment_login, view, false)
    }

    /**
     * Binds buttons, Edittext and TextViews from id to references in class
     */
    override fun onStart() {
        super.onStart()

        loginButton = fragmentContainer!!.findViewById(R.id.loginButton)
        loginButton!!.setOnClickListener { view1 -> loginAccount() }

        etEmail = fragmentContainer!!.findViewById(R.id.loginEmailInput)
        etPassword = fragmentContainer!!.findViewById(R.id.loginPasswordInput)

        tvEmail = fragmentContainer!!.findViewById(R.id.loginEmailPrompt)
        tvPassword = fragmentContainer!!.findViewById(R.id.loginPasswordPrompt)
        tvError = fragmentContainer!!.findViewById(R.id.loginError)
    }

    /**
     * Attempts to call loginAccount from fragmentContainer activity
     */
    private fun loginAccount() {
        accountManager.loginAccount(etEmail!!.text.toString(), etPassword!!.text.toString())
    }

    /**
     * Displaces exception message
     *
     * @param exception holder of error message to be displayed
     */
    internal fun displayLoginError(exception: Exception) {
        tvError!!.visibility = View.VISIBLE
        tvError!!.text = exception.message
    }

    /**
     * Shows or hides error message
     *
     * @param display boolean if error gets displayed or not
     */
    internal fun displayLoginError(display: Boolean?) {
        tvError!!.visibility = if (display!!) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Enables or disables all interactive elements of the fragment
     *
     * @param value to enable or disable elements
     */
    internal fun setFragmentInteraction(value: Boolean?) {
        etEmail!!.isEnabled = value!!
        etPassword!!.isEnabled = value
        loginButton!!.isEnabled = value


        tvEmail!!.isEnabled = value
        tvPassword!!.isEnabled = value

        etPassword!!.setText("")
    }
}
