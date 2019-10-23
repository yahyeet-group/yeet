package com.yahyeet.boardbook.presenter.matchcreation.configureteams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.HelperFunctions
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User

import java.util.ArrayList

class ConfigureTeamAdapter(private val ctp: ConfigureTeamPresenter) : RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder>() {

    private val myDataset = ArrayList<User>()

    var teamArray: MutableList<String> = ArrayList()


    inner class PlayerViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        private val teamSpinner: Spinner? = null
        private val roleSpinner: Spinner? = null
        private val user: User? = null

        val matchPlayer: MatchPlayer
            get() {
                val game = ctp.masterPresenter.cmdh.game
                var gameTeam: GameTeam? = null
                var gameRole: GameRole? = null


                if (teamSpinner!!.selectedItemPosition != 0) {
                    gameTeam = game!!.teams[teamSpinner.selectedItemPosition - 1]
                }

                if (roleSpinner!!.selectedItemPosition != 0 && gameTeam != null) {
                    gameRole = gameTeam.roles[roleSpinner.selectedItemPosition - 1]
                }

                return MatchPlayer(user, gameRole, gameTeam, true)
            }

    }

    init {
        myDataset.addAll(ctp.masterPresenter.cmdh.selectedPlayers)

        val teams = ArrayList(ctp.masterPresenter.cmdh.game!!.teams)
        val teamNull = GameTeam()
        teamNull.name = "No Team"
        teams.add(0, teamNull)
        for (gameTeam in teams) {
            teamArray.add(gameTeam.name)
        }
    }


    override fun onCreateViewHolder(vg: ViewGroup, viewType: Int): ConfigureTeamAdapter.PlayerViewHolder {


        val v = LayoutInflater.from(vg.context).inflate(R.layout.element_configure_teams, vg, false)

        return PlayerViewHolder(v)

    }

    override fun onBindViewHolder(holder: ConfigureTeamAdapter.PlayerViewHolder, position: Int) {

        holder.user = myDataset[position]

        val v = holder.itemView
        //Minimize on start
        v.layoutParams.height = HelperFunctions.dpFromPx(250, holder.itemView.context)
        v.requestLayout()

        val editButton = v.findViewById<Button>(R.id.spEditButton)
        val doneButton = v.findViewById<Button>(R.id.spDoneButton)

        editButton.setOnClickListener { n ->
            v.layoutParams.height = HelperFunctions.dpFromPx(250, holder.itemView.context)
            v.requestLayout()
        }
        doneButton.setOnClickListener { n ->
            v.layoutParams.height = HelperFunctions.dpFromPx(100, holder.itemView.context)
            v.requestLayout()
        }


        //// Spinner stuffs
        val teamSpinner = v.findViewById<Spinner>(R.id.teamSpinner)

        val teamAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, teamArray)    // Specify the layout to use when the list of choices appears
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        teamSpinner.adapter = teamAdapter


        val roleSpinner = v.findViewById<Spinner>(R.id.roleSpinner)

        teamSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (teamArray[position] == "No Team") {
                    roleSpinner.isEnabled = false
                    return
                }
                setRolesByTeam(ctp.masterPresenter.cmdh.game!!.teams[position - 1], holder.itemView, roleSpinner)
                roleSpinner.isEnabled = true

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                roleSpinner.isEnabled = false
            }
        }

        holder.roleSpinner = roleSpinner
        holder.teamSpinner = teamSpinner

    }


    override fun getItemCount(): Int {
        return myDataset.size

    }

    private fun setRolesByTeam(team: GameTeam, v: View, roleSpinner: Spinner) {
        val roles = ArrayList(team.roles)
        val rolesString = ArrayList<String>()
        val nullRole = GameRole()
        nullRole.name = "No Role"
        roles.add(0, nullRole)
        for (role in roles) {
            rolesString.add(role.name)
        }
        val roleAdapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, rolesString)    // Specify the layout to use when the list of choices appears
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = roleAdapter

    }

    private fun finalizeMatch() {

    }


}
