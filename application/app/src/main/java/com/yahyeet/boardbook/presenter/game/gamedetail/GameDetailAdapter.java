package com.yahyeet.boardbook.presenter.game.gamedetail;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the game detail recycler view
 */
public class GameDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int FIRST_LIST_ITEM_VIEW = 1;
	private static final int SECOND_LIST_ITEM_VIEW = 2;

	private List<GameTeam> firstList;
	private List<List<GameRole>> secondList;

	private List<String> allNames = new ArrayList<>();

	public GameDetailAdapter(List<GameTeam> firstList, List<List<GameRole>> secondList) {
		this.firstList = firstList;
		this.secondList = secondList;

		for (GameTeam team : firstList){
			allNames.add(team.getName());
			if(!team.getRoles().isEmpty()){
				for(GameRole role : team.getRoles())
					allNames.add(role.getName());
			}
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		// List items of first list
		private TextView tvTeamName;

		// List items of second list
		private TextView tvRoleName;


		public ViewHolder(final View itemView) {
			super(itemView);

			// Get the view of the elements of first list
			tvTeamName = itemView.findViewById(R.id.gameDetailTeamName);

			// Get the view of the elements of second list
			tvRoleName =  itemView.findViewById(R.id.gameDetailRoleName);
		}

		void bindViewSecondList(int pos) {

			final String description = allNames.get(pos);

			tvRoleName.setText(description);
		}

		void bindViewFirstList(int pos) {

			final String description = allNames.get(pos);

			tvTeamName.setText(description);
		}


	}

	private class FirstListItemViewHolder extends ViewHolder {
		public FirstListItemViewHolder(View itemView) {
			super(itemView);
		}
	}


	private class SecondListItemViewHolder extends ViewHolder {
		public SecondListItemViewHolder(View itemView) {
			super(itemView);
		}
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View v;

		if (viewType == FIRST_LIST_ITEM_VIEW) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_team, parent, false);
			FirstListItemViewHolder vh = new FirstListItemViewHolder(v);
			return vh;

		} else {
			// SECOND_LIST_ITEM_VIEW
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_role, parent, false);
			SecondListItemViewHolder vh = new SecondListItemViewHolder(v);
			return vh;
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		try {
			if (holder instanceof SecondListItemViewHolder) {
				SecondListItemViewHolder vh = (SecondListItemViewHolder) holder;
				vh.bindViewSecondList(position);

			} else if (holder instanceof FirstListItemViewHolder) {
				FirstListItemViewHolder vh = (FirstListItemViewHolder) holder;
				vh.bindViewFirstList(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {

		int firstListSize = 0;
		int secondListSize = 0;

		if (secondList == null && firstList == null) return 0;


		if (firstList != null)
			firstListSize = firstList.size();


		for (List<GameRole> list : secondList) {
			for (GameRole ignored : list)
				secondListSize++;
		}

		return firstListSize + secondListSize;
	}

	@Override
	public int getItemViewType(int position) {


		if (secondList == null && firstList == null)
			return super.getItemViewType(position);


		if (secondList != null) {
			int i = (int) secondList.stream().filter(list -> !list.isEmpty()).count();
			if (i == 0)
				return FIRST_LIST_ITEM_VIEW;
		}


		if (firstList == null)
			return SECOND_LIST_ITEM_VIEW;


		List<Integer> sizeList = new ArrayList<>();

		for (List<GameRole> list : secondList)
			if(!list.isEmpty())
				sizeList.add(list.size());

		int desiredPosition = position;

		int sumOfPreviousTeamSizes = 0;
		for (int i = 0; i < sizeList.size(); i++) {
			if (sumOfPreviousTeamSizes + sizeList.get(i) >= position) {
				desiredPosition = position - sumOfPreviousTeamSizes - i;
				break;
			} else {
				sumOfPreviousTeamSizes += sizeList.get(i);
			}
		}

		if (desiredPosition == 0)
			return FIRST_LIST_ITEM_VIEW;
		else
			return SECOND_LIST_ITEM_VIEW;
	}
}
