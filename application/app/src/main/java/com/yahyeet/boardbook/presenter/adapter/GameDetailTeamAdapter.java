package com.yahyeet.boardbook.presenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDetailTeamAdapter extends RecyclerView.Adapter<GameDetailTeamAdapter.GameDetailViewHolder> {


    private List<String> myDataset;
    private Map<String, List<String>> roleMap;

    static class GameDetailViewHolder extends RecyclerView.ViewHolder {

        // TODO Replace this area with match class as a custom view object
        private TextView gameTeamName;
        private RecyclerView roleList;

        private View view;


        GameDetailViewHolder(View v) {
            super(v);

            view = v;

            gameTeamName = v.findViewById(R.id.gameDetailTeamName);
            roleList = v.findViewById(R.id.gameDetailTeamRoles);
        }


    }


    public GameDetailTeamAdapter(List<String> dataset) {
        if (dataset != null)
            myDataset = dataset;
        else {
            myDataset = new ArrayList<>();
            myDataset.add("");
        }


    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public GameDetailViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                   int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_detail_team_element, viewGroup, false);

        return new GameDetailViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(GameDetailViewHolder holder, int position) {
        // TODO: Replace with model integration when game is implemented

        holder.gameTeamName.setText("Team Name");


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.view.getContext());
        holder.roleList.setLayoutManager(layoutManager);

        List<String> teamRoleList = roleMap.get(holder.gameTeamName.getText().toString());
        GameDetailRoleAdapter roleAdapter = new GameDetailRoleAdapter(teamRoleList);

        holder.roleList.setAdapter(roleAdapter);

    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    /*
    private static final String TAG = "MatchAdapter";
    private Context view;
    private List<String> teamNames;
    private HashMap<String, List<String>> roleNames;

    public GameDetailTeamAdapter(Context view, List<List<String>> teamSetup){
        this.view = view;

        for(List<String> team : teamSetup){
            teamNames.add(team.get(0));
            List<String> temp = team.subList(1, team.size());
            roleNames.put(team.get(0), temp);
        }
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return roleNames.get(teamNames.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.view
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.game_detail_role, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.gameDetailRoleText);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return roleNames.get(this.teamNames.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return teamNames.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return teamNames.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) view.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.game_detail_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.gameDetailListHeader);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }*/


}
