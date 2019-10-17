package com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CreateMatchDataHandler;
import com.yahyeet.boardbook.activity.CreateingMatches.HelperFunctions;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ConfigureTeamAdapter extends RecyclerView.Adapter<ConfigureTeamAdapter.PlayerViewHolder> {

	private User[] myDataset = {new User()};
	private int playeradded = 0;


	class PlayerViewHolder extends RecyclerView.ViewHolder {


		PlayerViewHolder(View v) {
			super(v);

		}

	}

	public ConfigureTeamAdapter() {
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
			System.out.println("YEEEEEEEEEEEEEET!");
			v.getLayoutParams().height = HelperFunctions.dpFromPx(100, holder.itemView.getContext());
			v.requestLayout();
		});

		Spinner teamSpinner = v.findViewById(R.id.teamSpinner);
		List<String> spinnerArray = new ArrayList<String>();
		spinnerArray.add("Minions Of Mordred");
		ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, spinnerArray);    // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teamSpinner.setAdapter(adapter);
	}


	@Override
	public int getItemCount() {
		return myDataset.length;
	}
}
