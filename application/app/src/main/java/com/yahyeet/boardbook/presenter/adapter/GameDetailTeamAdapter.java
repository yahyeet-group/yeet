package com.yahyeet.boardbook.presenter.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yahyeet.boardbook.R;

import java.util.HashMap;
import java.util.List;

public class GameDetailTeamAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "MatchAdapter";
    private Context context;
    private List<String> teamNames;
    private HashMap<String, List<String>> roleNames;

    public GameDetailTeamAdapter(Context context, List<List<String>> teamSetup){
        this.context = context;

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
            LayoutInflater layoutInflater = (LayoutInflater) this.context
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
            LayoutInflater layoutInflater = (LayoutInflater) context.
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
    }

    /*static class GameDetailViewHolder extends RecyclerView.ViewHolder {

        // TODO Replace this area with match class as a custom view object
        private TextView gameTeamName;
        private ListView roleList;



        GameDetailViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));

            gameTeamName = v.findViewById(R.id.gameDetailTeamName);
            roleList = v.findViewById(R.id.gameDetailRoleList);
        }


    }


    public GameDetailTeamAdapter(List<Game> dataset) {
        if (dataset != null)
            myDataset = dataset;
        else{
            myDataset = new ArrayList<>();
            myDataset.add(new Game());
        }


    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public GameDetailViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                              int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_detail_header, viewGroup, false);

        return new GameDetailViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(GameDetailViewHolder holder, int position) {
        // TODO: Replace with model integration when game is implemented

        holder.gameTeamName.setText("Team Name");
        //holder.imageView.setImageURI();
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }*/


}
