package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.SelectPlayerPresenter;

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

        Button cssBtn = v.findViewById(R.id.cssTestBtn);
        Button csbBtn = v.findViewById(R.id.csbTestingBtn);
        Button ccBtn = v.findViewById(R.id.ccTestingBtn);

        cssBtn.setOnClickListener((n) -> {
            ConstraintLayout layout = v.findViewById(R.id.testingLayout);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
            layoutParams.height = 250;
            layoutParams.width = 250;
            layout.setLayoutParams(layoutParams);
        });
        csbBtn.setOnClickListener((n) -> {
            testView.getLayoutParams().height = 300;
            testView.requestLayout();
        });
        ccBtn.setOnClickListener((n) -> {
            testView.setBackgroundColor(101010);
        });
    }
}