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
import com.yahyeet.boardbook.activity.matchcreation.HelperFunctions;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigureTeamAdapter extends RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder> {

	private List<User> myDataset = new ArrayList<>();
	private ConfigureTeamPresenter ctp;

	private List<String> teamArray;

	class PlayerViewHolder extends RecyclerView.ViewHolder {


		PlayerViewHolder(View v) {
			super(v);

		}

	}

	public ConfigureTeamAdapter(ConfigureTeamPresenter ctp) {
		this.ctp = ctp;
		myDataset.addAll(ctp.getMasterPresenter().getCmdh().getSelectedPlayers());

		List<GameTeam> teams = new ArrayList<>(ctp.getMasterPresenter().getCmdh().getGame().getTeams());
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

		View v = holder.itemView;
		//Minimize on start
		v.getLayoutParams().height = HelperFunctions.dpFromPx(250, holder.itemView.getContext());
		v.requestLayout();

		Button editButton = v.findViewById(R.id.spEditButton);
		Button doneButton = v.findViewById(R.id.spDoneButton);

		editButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = HelperFunctions.dpFromPx(250, holder.itemView.getContext());
			v.requestLayout();
		});
		doneButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = HelperFunctions.dpFromPx(100, holder.itemView.getContext());
			v.requestLayout();
		});


		//// Spinner stuffs
		Spinner teamSpinner = v.findViewById(R.id.teamSpinner);

		ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, teamArray);    // Specify the layout to use when the list of choices appears
		teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(teamAdapter);

		teamSpinner.setOnItemClickListener((parent, view, position1, id) ->
			setRolesByTeam(ctp.getMasterPresenter().getCmdh().getGame().getTeams().get(position1), holder.itemView));


	}


	@Override
	public int getItemCount() {
		return myDataset.size();
	}

	private List<GameRole> setRolesByTeam(GameTeam team, View v){
		Spinner roleSpinner = v.findViewById(R.id.roleSpinner);
		List<GameRole> roles = new ArrayList<>(team.getRoles());
		List<String> rolesString = new ArrayList<>();
		for(GameRole role : roles){
			rolesString.add(role.getName());
		}
		ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, rolesString);    // Specify the layout to use when the list of choices appears
		roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(roleAdapter);

		return null;
	}
}
