package com.yahyeet.boardbook.presenter

import android.content.Context
import android.content.Intent

import com.yahyeet.boardbook.activity.home.IHomeActivity
import com.yahyeet.boardbook.activity.profile.ProfileActivity
import com.yahyeet.boardbook.activity.account.AccountManagerActivity

class HomePresenter(private val homeActivity: IHomeActivity) {


    fun startLoggedInUserProfile() {
        val context = homeActivity as Context
        val startProfile = Intent(context, ProfileActivity::class.java)
        startProfile.putExtra("UserId", BoardbookSingleton.getInstance().authHandler.loggedInUser!!.id)
        context.startActivity(startProfile)
    }

    fun logOut() {
        BoardbookSingleton.getInstance().authHandler.logout()
        val context = homeActivity as Context
        val startLogin = Intent(context, AccountManagerActivity::class.java)
        context.startActivity(startLogin)
    }
}
