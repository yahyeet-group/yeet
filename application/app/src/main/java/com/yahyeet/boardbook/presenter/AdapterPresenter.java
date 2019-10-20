package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.handler.EntityHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterPresenter<E extends AbstractEntity, H extends EntityHandler<E>> {

	private AbstractAdapter<E> adapter;
	private IFutureInteractable fragment;
	private List<E> database;

	public AdapterPresenter(IFutureInteractable fragment) {
		database = new ArrayList<>();
		this.fragment = fragment;
	}

	protected void pullAllDataToDatabase(H handler) {

		Handler uiHandler = new android.os.Handler(Looper.getMainLooper());


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

	protected AbstractAdapter<E> getAdapter() {
		return adapter;
	}

	protected IFutureInteractable getFragment() {
		return fragment;
	}

	protected List<E> getDatabase() {
		return database;
	}

	public void setAdapter(AbstractAdapter<E> adapter) {
		this.adapter = adapter;
	}

}
