package com.yahyeet.boardbook.presenter;

import android.os.Handler;
import android.os.Looper;
import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.handler.EntityHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterPresenter<E extends AbstractEntity, H extends EntityHandler<E>> {

	private RecyclerView.Adapter adapter;
	private IFutureInteractable fragment;
	private List<E> database;

	private Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

	public AdapterPresenter(IFutureInteractable fragment) {
		this.fragment = fragment;
		database = new ArrayList<>();
	}

	protected void fillDatabase(H handler) {
		fragment.disableViewInteraction();
		handler.all().thenAccept(initiatedGames -> {
			if (initiatedGames != null) {
				database.addAll(initiatedGames);
			}

			uiHandler.post(() -> {
				fragment.enableViewInteraction();
				adapter.notifyDataSetChanged();
			});
		}).exceptionally(e -> {
			uiHandler.post(() -> {
				fragment.displayLoadingFailed();
				fragment.enableViewInteraction();
			});
			return null;
		});
	}

	protected void fillAndModifyDatabase(H handler) {
		fragment.disableViewInteraction();
		handler.all().thenAccept(initiatedGames -> {
			if (initiatedGames != null) {
				database.addAll(initiatedGames);
				modifyDatabase(database);

			}

			uiHandler.post(() -> {
				fragment.enableViewInteraction();
				adapter.notifyDataSetChanged();
			});
		}).exceptionally(e -> {
			uiHandler.post(() -> {
				fragment.displayLoadingFailed();
				fragment.enableViewInteraction();
			});
			return null;
		});
	}

	protected void updateAdapter(){
		adapter.notifyDataSetChanged();
	}

	protected RecyclerView.Adapter getAdapter() {
		return adapter;
	}

	protected IFutureInteractable getFragment() {
		return fragment;
	}

	protected List<E> getDatabase() {
		return database;
	}

	protected void setDatabase(List<E> newDatbase){
		database.clear();
		database.addAll(newDatbase);
	}

	protected void setAdapter(AbstractSearchAdapter<E> adapter) {
		this.adapter = adapter;
	}

	// To be overridden	, but not forced so not abstract
	protected void modifyDatabase(List<E> database){
		// Called in fillAndModifyDatabase, override if database should not be all entities of type T
	}

}