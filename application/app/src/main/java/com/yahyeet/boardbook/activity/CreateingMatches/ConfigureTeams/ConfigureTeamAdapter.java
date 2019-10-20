package com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.CreateMatchDataHandler;
import com.yahyeet.boardbook.activity.CreateingMatches.HelperFunctions;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.ConfigureTeamPresenter;

import java.util.ArrayList;
import java.util.List;

public class ConfigureTeamAdapter extends RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder> {

	private List<User> myDataset = new ArrayList<>();
	private int playeradded = 0;
	private ConfigureTeamPresenter ctp;


	class PlayerViewHolder extends RecyclerView.ViewHolder {


		PlayerViewHolder(View v) {
			super(v);

		}

	}

	public ConfigureTeamAdapter(ConfigureTeamPresenter ctp) {
		this.ctp = ctp;
		myDataset.addAll(ctp.getMasterPresenter().getCmdh().getSelectedPlayers());

	}


	@Override
	public ConfigureTeamAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {


		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.configure_teams_element, vg, false);

		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(ConfigureTeamAdapter.PlayerViewHolder holder, int position) {

		View v = holder.itemView;

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
		List<String> teamArray = new ArrayList<>();
		//teamArray.addAll(ctp.getMasterPresenter().getCmdh().getGame().getTeams());
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, teamArray);    // Specify the layout to use when the list of choices appears
		teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(teamAdapter);

		Spinner roleSpinner = v.findViewById(R.id.roleSpinner);
		List<String> roleArray = new ArrayList<>();
		roleArray.add("Minions Of Mordred");
		ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, roleArray);    // Specify the layout to use when the list of choices appears
		roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(roleAdapter);
	}


	@Override
	public int getItemCount() {
		return myDataset.size();
	}
}
