package com.yahyeet.boardbook.activity.account

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment

abstract class AccountFragment : Fragment() {

    internal lateinit var accountManager: IAccountFragmentHolder
    internal var fragmentContainer: View? = null

    internal var etEmail: EditText? = null
    internal var etPassword: EditText? = null

    /**
     * Saves reference of class inflating the fragment, casts to IAccountFragmentHolder
     *
     * @param context is the activity inflating the fragment
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            accountManager = context as IAccountFragmentHolder
        } catch (e: Exception) {
            // TODO: Implement consequences if fragment fragmentContainer activity does not implement IAccountManagerActivity
            e.printStackTrace()
        }

    }

    /**
     * Method displays error on login edittext
     * @param errorText error description
     */
    internal fun emailFailed(errorText: String) {
        etEmail!!.error = errorText
    }

    /**
     * Method displays error on password edittext
     * @param errorText error description
     */
    internal fun passwordFailed(errorText: String) {
        etPassword!!.error = errorText
    }
}
