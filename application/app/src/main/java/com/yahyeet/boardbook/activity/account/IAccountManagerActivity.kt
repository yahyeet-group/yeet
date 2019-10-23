package com.yahyeet.boardbook.activity.account

interface IAccountManagerActivity {

    /**
     * Starts the homepage and finishes the account manager.
     */
    fun finishAccountManager()

    /**
     * Enables all interactive elements of the activity
     */
    fun enableManagerInteraction()

    /**
     * Disables all interactive elements of the activity
     */
    fun disableManagerInteraction()

    /**
     * Handles where exceptions from presenter should be displayed in login fragment
     * @param exception contains message and is used to determine where exception should be displayed
     */
    fun loginFailed(exception: Exception)

    /**
     * Handles where exceptions from presenter should be displayed in register fragment
     * @param exception contains message and is used to determine where exception should be displayed
     */
    fun registerFailed(exception: Exception)

}
