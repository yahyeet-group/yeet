package com.yahyeet.boardbook.presenter;

import android.os.Handler;
import android.os.Looper;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.handler.EntityHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AdapterPresenter<E extends AbstractEntity, H extends EntityHandler<E>> {

	private AbstractAdapter<E> adapter;
	private IFutureInteractable fragment;
	private List<E> database;

	private Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

	public AdapterPresenter(IFutureInteractable fragment) {
		database = new ArrayList<>();
		this.fragment = fragment;
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

	protected AbstractAdapter<E> getAdapter() {
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

	protected void setAdapter(AbstractAdapter<E> adapter) {
		this.adapter = adapter;
	}

	// To be overriden, but not forced so not abstract
	protected void modifyDatabase(List<E> database){}

}
