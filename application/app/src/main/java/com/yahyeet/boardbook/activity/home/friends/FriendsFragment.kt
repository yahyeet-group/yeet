package com.yahyeet.boardbook.activity.home.friends

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.friends.FriendsPresenter

class FriendsFragment : Fragment(), IFriendFragment {

    private var friendsPresenter: FriendsPresenter? = null
    private var addFriendbtn: ImageButton? = null
    private var friendSearch: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        friendsPresenter = FriendsPresenter(this)


        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAllViews()
        friendsPresenter = FriendsPresenter(this)
        friendSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


            }

            override fun afterTextChanged(editable: Editable) {
                friendsPresenter!!.searchFriends(friendSearch!!.text.toString())
            }
        })


        enableFriendList()
    }

    private fun setAllViews() {

        val view = view

        friendSearch = view!!.findViewById(R.id.friendSearch)
        addFriendbtn = view.findViewById(R.id.addFriendButton)

        addFriendbtn!!.setOnClickListener { view1 ->
            val intent = Intent(context, AddFriendActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        friendsPresenter!!.notifyAdapter()
    }

    override fun enableFriendList() {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.friendsRecycler)
        context?.let { friendsPresenter!!.enableFriendsList(recyclerView, it) }
    }
}
