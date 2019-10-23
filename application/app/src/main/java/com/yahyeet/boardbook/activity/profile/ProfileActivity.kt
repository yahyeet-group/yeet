package com.yahyeet.boardbook.activity.profile

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.ProfilePresenter

class ProfileActivity : AppCompatActivity(), IProfileActivity {

    private var profilePresenter: ProfilePresenter? = null
    private val tvUsername: TextView? = null
    private val tvWinrate: TextView? = null
    private val tvGamesPlayed: TextView? = null
    private val pbWinrate: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userId: String?
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                userId = null
            } else {
                userId = extras.getString("UserId")
            }
        } else {
            // TODO: Maybe bad, idk // Vex
            userId = savedInstanceState.getSerializable("UserId") as String
        }

        profilePresenter = ProfilePresenter(this, userId)
        enableMatchFeed()
    }

    /**
     * Initiates recyclerView of matches in activity and populates it
     */
    fun enableMatchFeed() {
        // TODO: Examine how these method calls can get nullPointerException
        val matchRecycler = findViewById<RecyclerView>(R.id.rvProfile)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchRecycler.setHasFixedSize(true)
        profilePresenter!!.enableMatchFeed(matchRecycler, baseContext)
    }

}
