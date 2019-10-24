package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.viewutils.ViewUtils;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ConfigureTeamAdapter extends RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder> {

	private List<User> myDataset = new ArrayList<>();
	private ConfigureTeamPresenter ctp;


	public class PlayerViewHolder extends RecyclerView.ViewHolder {

		private Spinner teamSpinner;
		private Spinner roleSpinner;
		private User user;


		PlayerViewHolder(View v) {
			super(v);

		}

		public MatchPlayer getMatchPlayer() {
			Game game = ctp.getMasterPresenter().getCmdh().getGame();
			GameTeam gameTeam = null;
			GameRole gameRole = null;


			if (teamSpinner.getSelectedItemPosition() != 0) {
				gameTeam = game.getTeams().get(teamSpinner.getSelectedItemPosition() - 1);
			}

			if(roleSpinner.getSelectedItemPosition() != 0 && gameTeam != null){
				gameRole = gameTeam.getRoles().get(roleSpinner.getSelectedItemPosition() -1);
			}

			return new MatchPlayer(user, gameRole, gameTeam, true);
		}

	}

	public List<String> teamArray = new ArrayList<>();

	public ConfigureTeamAdapter(ConfigureTeamPresenter ctp) {
		this.ctp = ctp;
		myDataset.addAll(ctp.getMasterPresenter().getCmdh().getSelectedPlayers());

		List<GameTeam> teams = new ArrayList<>(ctp.getMasterPresenter().getCmdh().getGame().getTeams());
		GameTeam teamNull = new GameTeam();
		teamNull.setName("No Team");
		teams.add(0, teamNull);
		for (GameTeam gameTeam : teams) {
			teamArray.add(gameTeam.getName());
		}
	}


	@Override
	public ConfigureTeamAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {


		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.element_configure_teams, vg, false);

		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(ConfigureTeamAdapter.PlayerViewHolder holder, int position) {

		holder.user = myDataset.get(position);

		View v = holder.itemView;
		//Minimize on start
		v.getLayoutParams().height = ViewUtils.dpFromPx(250, holder.itemView.getContext());
		v.requestLayout();

		Button editButton = v.findViewById(R.id.spEditButton);
		Button doneButton = v.findViewById(R.id.spDoneButton);

		editButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = ViewUtils.dpFromPx(250, holder.itemView.getContext());
			v.requestLayout();
		});
		doneButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = ViewUtils.dpFromPx(100, holder.itemView.getContext());
			v.requestLayout();
		});


		//// Spinner stuffs
		Spinner teamSpinner = v.findViewById(R.id.teamSpinner);

		ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, teamArray);    // Specify the layout to use when the list of choices appears
		teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(teamAdapter);


		Spinner roleSpinner = v.findViewById(R.id.roleSpinner);

		teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (teamArray.get(position).equals("No Team")) {
					roleSpinner.setEnabled(false);
					return;
				}
				setRolesByTeam(ctp.getMasterPresenter().getCmdh().getGame().getTeams().get(position -1), holder.itemView, roleSpinner);
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

	private void setRolesByTeam(GameTeam team, View v, Spinner roleSpinner) {
		List<GameRole> roles = new ArrayList<>(team.getRoles());
		List<String> rolesString = new ArrayList<>();
		GameRole nullRole = new GameRole();
		nullRole.setName("No Role");
		roles.add(0, nullRole);
		for (GameRole role : roles) {
			rolesString.add(role.getName());
		}
		ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, rolesString);    // Specify the layout to use when the list of choices appears
		roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(roleAdapter);

	}

	private void finalizeMatch() {

	}


}
