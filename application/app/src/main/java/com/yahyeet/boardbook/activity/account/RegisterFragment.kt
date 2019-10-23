package com.yahyeet.boardbook.activity.account

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.yahyeet.boardbook.R

class RegisterFragment : AccountFragment() {

    private var etUser: EditText? = null

    private var tvEmail: TextView? = null
    private var tvPassword: TextView? = null
    private var tvUsername: TextView? = null

    private var registerButton: Button? = null


    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentContainer = view
        return inflater.inflate(R.layout.fragment_register, view, false)
    }


    override fun onStart() {
        super.onStart()
        etEmail = fragmentContainer!!.findViewById(R.id.registerEmailInput)
        etPassword = fragmentContainer!!.findViewById(R.id.registerPasswordInput)
        etUser = fragmentContainer!!.findViewById(R.id.registerUsernameInput)
        registerButton = fragmentContainer!!.findViewById(R.id.registerButton)

        tvUsername = fragmentContainer!!.findViewById(R.id.registerUsernameText)
        tvEmail = fragmentContainer!!.findViewById(R.id.registerEmailText)
        tvPassword = fragmentContainer!!.findViewById(R.id.registerPasswordText)

        registerButton!!.setOnClickListener { view1 -> registerAccount() }

        etEmail!!.addTextChangedListener(EmailWatcher(etEmail!!, resources))
        etPassword!!.addTextChangedListener(PasswordWatcher(etPassword!!, resources))
    }

    /**
     * Attempts to call registerAccount from fragmentContainer activity
     */
    private fun registerAccount() {
        accountManager.registerAccount(etEmail!!.text.toString(), etPassword!!.text.toString(), etUser!!.text.toString())
    }

    /**
     * Enables or disables all interactive elements of the fragment
     *
     * @param value enable or disable value
     */
    internal fun setFragmentInteraction(value: Boolean?) {
        etEmail!!.isEnabled = value!!
        etPassword!!.isEnabled = value
        etUser!!.isEnabled = value
        registerButton!!.isEnabled = value

        tvUsername!!.isEnabled = value
        tvEmail!!.isEnabled = value
        tvPassword!!.isEnabled = value

        etPassword!!.setText("")

    }

    internal class EmailWatcher(var emailText: EditText, var res: Resources) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) {
                emailText.error = res.getString(R.string.emailFormatError)
            }
        }

        override fun afterTextChanged(editable: Editable) {


        }
    }

    internal class PasswordWatcher(var passwordText: EditText, var res: Resources) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (passwordText.text.toString().length <= 6 && !passwordText.text.toString().isEmpty()) {

                passwordText.error = res.getString(R.string.passwordLengthError)
            }
        }

        override fun afterTextChanged(editable: Editable) {


        }
    }


}


