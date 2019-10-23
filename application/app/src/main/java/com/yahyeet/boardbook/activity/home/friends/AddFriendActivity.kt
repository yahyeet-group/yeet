package com.yahyeet.boardbook.activity.home.friends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.presenter.friends.addfriends.AddFriendPresenter

class AddFriendActivity : AppCompatActivity(), IAddFriendActivity, IFutureInteractable {

    private var addFriendPresenter: AddFriendPresenter? = null
    private var tvAddFriendSearch: EditText? = null
    private var tvError: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfriend)

        tvError = findViewById(R.id.addFriendError)
        tvAddFriendSearch = findViewById(R.id.addFriendSearch)


    }

    override fun onStart() {
        super.onStart()

        addFriendPresenter = AddFriendPresenter(this)
        enableAddFriendList()

        tvAddFriendSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                addFriendPresenter!!.searchNonFriends(tvAddFriendSearch!!.text.toString())
            }
        })
    }

    override fun enableAddFriendList() {
        val recyclerView = findViewById<RecyclerView>(R.id.addFriendsRecycle)
        addFriendPresenter!!.enableAddFriendsList(recyclerView, this)
    }

    override fun disableViewInteraction() {
        tvAddFriendSearch!!.isEnabled = false
        findViewById<View>(R.id.addFriendLoadlingLayout).visibility = View.VISIBLE

    }

    override fun enableViewInteraction() {
        tvAddFriendSearch!!.isEnabled = true
        findViewById<View>(R.id.addFriendLoadlingLayout).visibility = View.INVISIBLE

    }

    override fun displayLoadingFailed() {
        tvError!!.visibility = View.VISIBLE
    }

    override fun finishAddFriendActivity() {
        finish()
    }
}
