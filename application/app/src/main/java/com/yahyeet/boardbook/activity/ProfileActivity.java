package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements IProfileActivity {

    private ProfilePresenter profilePresenter;
    private TextView tvUsername;
    private TextView tvWinrate;
    private TextView tvGamesPlayed;
    private ProgressBar pbWinrate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePresenter = new ProfilePresenter(this);

        tvUsername = findViewById(R.id.tvUsername);
        tvWinrate = findViewById(R.id.tvWinrate);
        tvGamesPlayed = findViewById(R.id.tvGamesPlayed);
        pbWinrate = findViewById(R.id.pbWinrate);

        setDataFields();
    }

    private void setDataFields() {
        tvUsername.setText(profilePresenter.getLoggedInUserName());
        tvWinrate.setText(profilePresenter.getWinratePercentageForLoggedInUser());
        tvGamesPlayed.setText(profilePresenter.getGamesPlayedForLoggedInUser());
        pbWinrate.setProgress((int) Math.round(profilePresenter.getWinrateQuotaForLoggedInUser() * 100));
    }
}
