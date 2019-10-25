package com.yahyeet.boardbook.presenter;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.google.errorprone.annotations.ForOverride;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.handler.IEntityHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstracts presenters that can find all of an entity
 * @param <E> An entity
 * @param <H> Handler for entity E
 */
public abstract class FindAllPresenter<E extends AbstractEntity, H extends IEntityHandler<E>> {


	private RecyclerView.Adapter adapter;
	private IFutureInteractable fragment;
	private List<E> database;

	private Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

	public FindAllPresenter(IFutureInteractable fragment) {
		this.fragment = fragment;
		database = new ArrayList<>();
	}

	/**
	 * Finds all entities of a specific type
	 * @param handler defines what type of entity to be found
	 * @param config defines if entities should be populated
	 */
	protected void fillDatabase(H handler, Map<String, Boolean> config) {
		fragment.disableViewInteraction();
		handler.all(config).thenAccept(initiatedEntities -> {
			if (initiatedEntities != null) {
				database.addAll(initiatedEntities);
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

	/**
	 * Finds all entities of a specific type, also calls onDatabaseLoaded
	 * @param handler defines what type of entity to be found
	 * @param config defines if entities should be populated
	 */
	protected void fillAndModifyDatabase(H handler, Map<String, Boolean> config) {
		fragment.disableViewInteraction();
		handler.all(config).thenAccept(initiatedEntities -> {
			if (initiatedEntities != null) {
				database.addAll(initiatedEntities);
			}

			uiHandler.post(() -> {
				fragment.enableViewInteraction();
				adapter.notifyDataSetChanged();

				// Safety in case of modification affecting UI elements
				onDatabaseLoaded(database);
			});
		}).exceptionally(e -> {
			uiHandler.post(() -> {
				fragment.displayLoadingFailed();
				fragment.enableViewInteraction();
			});
			return null;
		});
	}

	@ForOverride
	protected void onDatabaseLoaded(List<E> database){
		// Called in fillAndModifyDatabase, override if database should not be all entities of type T
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

	protected void setAdapter(RecyclerView.Adapter adapter) {
		this.adapter = adapter;
	}

}
