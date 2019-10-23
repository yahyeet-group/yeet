package com.yahyeet.boardbook.activity

interface IFutureInteractable {

    /**
     * Enables all interactive elements of the activity
     */
    fun enableViewInteraction()

    /**
     * Disables all interactive elements of the activity
     */
    fun disableViewInteraction()

    /**
     * Displays error message of games not loading correctly
     */
    fun displayLoadingFailed()
}
