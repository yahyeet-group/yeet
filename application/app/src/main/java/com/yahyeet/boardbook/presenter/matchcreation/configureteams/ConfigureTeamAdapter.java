package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.viewutils.ViewUtils;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the adapter that creates and configures the views in the ConfigureTeamsRecycleView
 */
public class ConfigureTeamAdapter extends RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder> {

	private List<User> myDataset = new ArrayList<>();
	private ConfigureTeamPresenter ctp;

	private List<String> teamNames = new ArrayList<>();

	public class PlayerViewHolder extends RecyclerView.ViewHolder {

		private Spinner teamSpinner;
		private Spinner roleSpinner;
		private CheckBox winLoseBox;

		PlayerViewHolder(View v) {
			super(v);

		}

		/**
		 * Returns the spinners for one entire in the RecycleView
		 * @return an array with Spinners
		 */
		public Spinner[] getSpinners() {
			return new Spinner[]{teamSpinner, roleSpinner};
		}

		/**
		 * Returns the boolean value of the win/lose checkbox
		 * @return a boolean
		 */
		public Boolean getWin() {
			return winLoseBox.isChecked();
		}

	}


	public ConfigureTeamAdapter(ConfigureTeamPresenter ctp) {
		this.ctp = ctp;
		myDataset.addAll(ctp.getMasterPresenter().getCmdh().getSelectedPlayers());

		// Adds a default team called no team which is purely for placeholding in the spinners.
		List<GameTeam> teams = new ArrayList<>(ctp.getMasterPresenter().getCmdh().getGame().getTeams());

		GameTeam teamNull = new GameTeam();
		teamNull.setName("No Team");
		teams.add(0, teamNull);

		teams.forEach(team -> teamNames.add(team.getName()));


	}


	@Override
	public ConfigureTeamAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {


		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.element_configure_teams, vg, false);

		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(ConfigureTeamAdapter.PlayerViewHolder holder, int position) {

		View v = holder.itemView;

		//// Username binding
		TextView username = v.findViewById(R.id.ctPlayerName);
		username.setText(myDataset.get(position).getName());

		//Minimize on start
		v.getLayoutParams().height = ViewUtils.dpFromPx(250, holder.itemView.getContext());
		v.requestLayout();

		//// Button Binding
		Button editButton = v.findViewById(R.id.spEditButton);
		Button doneButton = v.findViewById(R.id.spDoneButton);

		//// Bind buttons with listeners
		editButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = ViewUtils.dpFromPx(250, holder.itemView.getContext());
			v.requestLayout();
			editButton.setVisibility(View.INVISIBLE);
		});
		doneButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = ViewUtils.dpFromPx(100, holder.itemView.getContext());
			v.requestLayout();
			editButton.setVisibility(View.VISIBLE);
		});

		//// Winning checkbox binding
		holder.winLoseBox = v.findViewById(R.id.winCheckbox);

		//// Spinner intit and databinding
		Spinner teamSpinner = v.findViewById(R.id.teamSpinner);

		//Configuring the spinner
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, teamNames);
		teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(teamAdapter);


		Spinner roleSpinner = v.findViewById(R.id.roleSpinner);

		//// The event listener configuration fo the spinners
		//// If a team other than no team is selected the roles for the appropriate team will be loaded
		//// into the role spinner. And if no team is selected the role spinner will be disabled
		teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (teamNames.get(position).equals("No Team")) {
					roleSpinner.setEnabled(false);
					return;
				}
				setRolesByTeam(ctp.getMasterPresenter().getCmdh().getGame().getTeams().get(position - 1), holder.itemView, roleSpinner);
				roleSpinner.setEnabled(true);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				roleSpinner.setEnabled(false);
			}
		});

		holder.roleSpinner = roleSpinner;
		holder.teamSpinner = teamSpinner;

	}


	@Override
	public int getItemCount() {
		return myDataset.size();

	}


	//// Method to change the roles depending on what team was chosen in the team spinner
	private void setRolesByTeam(GameTeam team, View v, Spinner roleSpinner) {
		List<GameRole> roles = new ArrayList<>();

		List<String> rolesString = new ArrayList<>();
		GameRole nullRole = new GameRole();
		nullRole.setName("No Role");
		roles.add(0, nullRole);

		roles.addAll(team.getRoles());

		for (GameRole role : roles) {
			rolesString.add(role.getName());
		}
		ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, rolesString);
		roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(roleAdapter);

	}
}
