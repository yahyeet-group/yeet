package com.yahyeet.boardbook.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.errorprone.annotations.ForOverride;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.handler.EntityHandler;


public class OneEntityPresenter<E extends AbstractEntity, H extends EntityHandler<E>> {

	private IFutureInteractable fragment;

	// Entity could be null if id does not lead to an entity in database
	private E entity = null;

	private Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

	public OneEntityPresenter(IFutureInteractable fragment) {
		this.fragment = fragment;
	}


	protected void findEntity(H handler, String entityId) {
		fragment.disableViewInteraction();

		handler.find(entityId).thenAccept(initiatedEntity -> {
			if (initiatedEntity != null) {
				entity = initiatedEntity;
				uiHandler.post(() -> {
					fragment.enableViewInteraction();

					// Safety in case of modification affecting UI elements
					onEntityFound(entity);
				});
			}
			else{
				uiHandler.post(() -> {
					fragment.enableViewInteraction();
				});
			}


		}).exceptionally(e -> {
			uiHandler.post(() -> {
				fragment.displayLoadingFailed();
				fragment.enableViewInteraction();
			});
			return null;
		});
	}

	protected IFutureInteractable getFragment() {
		return fragment;
	}

	public E getEntity() {
		return entity;
	}

	@ForOverride
	protected void onEntityFound(E entity) {
		// Method called when entity has been found with said entity as parameter
		// Override if subclass want's info from entity after future completion
	}

}
