package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.GameDetailPresenter;
import com.yahyeet.boardbook.presenter.GamePresenter;

import java.util.List;

public class GameDetailActivity extends AppCompatActivity implements IGameDetailActivity{


    private GameDetailPresenter gameDetailPresenter;
    private TextView gameName;
    private TextView gameDescription;
    private TextView gameRules;
    private ImageView gameImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        String gameID;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                gameID = null;
            } else {
                gameID = extras.getString("Game");
            }
        } else {
            gameID = (String) savedInstanceState.getSerializable("Game");
        }

        gameDetailPresenter = new GameDetailPresenter(this, gameID);

        gameName = findViewById(R.id.gameDetailName);
        gameDescription = findViewById(R.id.gameDetailDescription);
        gameRules = findViewById(R.id.gameDetailRules);



    }

    protected void onStart() {
        super.onStart();
        gameDetailPresenter.initiateGameDetail();
    }

    public void setGameName(String name) {
        this.gameName.setText(name);
    }

    public void setGameDescription(String description) {
        this.gameDescription.setText(description);
    }

    public void setGameRules(String rules) {
        this.gameRules.setText(rules);
    }

}
