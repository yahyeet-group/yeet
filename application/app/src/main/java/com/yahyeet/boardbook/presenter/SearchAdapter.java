package com.yahyeet.boardbook.presenter;

import android.widget.Filter;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchAdapter<T extends AbstractEntity> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<T> database;
	private List<T> allEntities;
	private Filter filter;

	public SearchAdapter(List<T> database) {

		if(database != null)
			this.database = database;
		else
			this.database = new ArrayList<>();


		allEntities = new ArrayList<>(this.database);
		filter = createNewFilter();

	}

	@Override
	public int getItemCount() {
		return database.size();
	}

	public Filter getFilter() {
		return filter;
	}

	public List<T> getDatabase() {
		return database;
	}

	public List<T> getAllEntities() {
		if(database.size() > 0 && allEntities.size() == 0)
			allEntities = new ArrayList<>(database);
		return allEntities;
	}

	protected abstract Filter createNewFilter();
}
