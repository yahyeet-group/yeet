package com.yahyeet.boardbook.activity.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.home.HomeActivity
import com.yahyeet.boardbook.model.util.EmailFailedException
import com.yahyeet.boardbook.model.util.PasswordFailedException
import com.yahyeet.boardbook.presenter.AccountManagerPresenter

class AccountManagerActivity : AppCompatActivity(), IAccountManagerActivity, IAccountFragmentHolder {

    private var accountManagerPresenter: AccountManagerPresenter? = null
    private var loginFragment: LoginFragment? = null
    private var registerFragment: RegisterFragment? = null
    private var registerSwitchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)

        accountManagerPresenter = AccountManagerPresenter(this)
        registerSwitchButton = findViewById(R.id.registerSwitchButton)

        registerSwitchButton!!.setOnClickListener { view -> showRegisterFragment() }

        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()

        supportFragmentManager.beginTransaction().replace(R.id.accountManagerContainer, loginFragment!!, "Login").commit()
    }


    override fun loginAccount(email: String, password: String) {
        try {
            loginFragment!!.displayLoginError(false)
            accountManagerPresenter!!.loginAccount(email, password)
        } catch (e: EmailFailedException) {
            loginFailed(e)
        } catch (e: PasswordFailedException) {
            loginFailed(e)
        }

    }


    override fun registerAccount(email: String, password: String, username: String) {
        try {
            accountManagerPresenter!!.registerAccount(email, password, username)
        } catch (e: EmailFailedException) {
            registerFailed(e)
        } catch (e: PasswordFailedException) {
            registerFailed(e)
        }

    }


    override fun finishAccountManager() {
        val startHome = Intent(this, HomeActivity::class.java)
        startActivity(startHome)
        finish()
    }


    override fun onBackPressed() {
        if (registerFragment == supportFragmentManager.findFragmentByTag("Register"))
            showLoginFragment()
        else
            super.onBackPressed()
    }


    override fun loginFailed(exception: Exception) {
        if (exception is EmailFailedException)
            exception.message?.let { loginFragment!!.emailFailed(it) }
        else if (exception is PasswordFailedException)
            exception.message?.let { loginFragment!!.passwordFailed(it) }
        else {
            loginFragment!!.displayLoginError(exception)
        }
    }


    override fun registerFailed(exception: Exception) {
        if (exception is EmailFailedException)
            exception.message?.let { registerFragment!!.emailFailed(it) }
        else if (exception is PasswordFailedException)
            exception.message?.let { registerFragment!!.passwordFailed(it) }
    }


    override fun enableManagerInteraction() {

        enableFragment()
        registerSwitchButton!!.isEnabled = true
        findViewById<View>(R.id.accountLoadingLayout).visibility = View.INVISIBLE
    }


    override fun disableManagerInteraction() {
        disableFragment()
        registerSwitchButton!!.isEnabled = false
        findViewById<View>(R.id.accountLoadingLayout).visibility = View.VISIBLE
    }

    /**
     * Changes the currently visible fragment to the Login Fragment
     */
    private fun showLoginFragment() {
        registerSwitchButton!!.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.accountManagerContainer, loginFragment!!, "Login").commit()
    }

    /**
     * Changes the currently visible fragment to the Register Fragment
     */
    private fun showRegisterFragment() {
        registerSwitchButton!!.visibility = View.INVISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.accountManagerContainer, registerFragment!!, "Register").commit()
    }

    /**
     * Enables the currently active fragment
     */
    private fun enableFragment() {
        if (registerFragment == supportFragmentManager.findFragmentByTag("Register"))
            registerFragment!!.setFragmentInteraction(true)
        else {
            loginFragment!!.setFragmentInteraction(true)
        }
        registerSwitchButton!!.isEnabled = true
    }

    /**
     * Disables the currently active fragment
     */
    private fun disableFragment() {
        if (registerFragment == supportFragmentManager.findFragmentByTag("Register"))
            registerFragment!!.setFragmentInteraction(false)
        else {
            loginFragment!!.setFragmentInteraction(false)
        }
        registerSwitchButton!!.isEnabled = false

    }


}
