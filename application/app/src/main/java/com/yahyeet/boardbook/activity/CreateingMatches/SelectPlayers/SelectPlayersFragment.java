package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.SelectPlayerPresenter;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayersFragment extends Fragment implements ISelectPlayersFragment {

	private SelectPlayerPresenter spp;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		spp = new SelectPlayerPresenter(this);
		return inflater.inflate(R.layout.fragment_select_players, container, false);

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		bindTestButtons();
	}

	private void bindTestButtons() {
		View v = getView();
		View testView = v.findViewById(R.id.testingLayout);

		Button cssBtn = v.findViewById(R.id.spTestButton);
		Button editButton = v.findViewById(R.id.spEditButton);
		Button doneButton = v.findViewById(R.id.spDoneButton);


		//// SPINNER INIT
		List<String> spinnerArray =  new ArrayList<String>();
		spinnerArray.add("Minions Of Mordred");
		Spinner teamSpinner = v.findViewById(R.id.teamSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		teamSpinner.setAdapter(adapter);

		cssBtn.setOnClickListener((n) -> {
			testView.getLayoutParams().height = dpFromPx(100);
			testView.requestLayout();
		});
		editButton.setOnClickListener((n) -> {
			testView.getLayoutParams().height = dpFromPx(250);
			testView.requestLayout();
		});
		doneButton.setOnClickListener((n) -> {
			System.out.println("YEEEEEEEEEEEEEET!");
			testView.getLayoutParams().height = dpFromPx(100);
			testView.getLayoutParams();
		});
		/*
			csbBtn.setOnClickListener((n) -> {
			testView.getLayoutParams().height = 400;
			testView.requestLayout();
			*/
	/*});
		ccBtn.setOnClickListener((n) -> {
			testView.setBackgroundColor(101010);
		});*/
	}

	public int dpFromPx(int px) {
		return (int) (px * getContext().getResources().getDisplayMetrics().density);
	}
}