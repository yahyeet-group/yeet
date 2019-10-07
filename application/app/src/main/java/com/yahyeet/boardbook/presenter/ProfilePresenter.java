package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.IProfileActivity;

public class ProfilePresenter {

    private IProfileActivity profileActivity;

    public ProfilePresenter(IProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
    }

    public String getLoggedInUserName() {
        return BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getName();
    }

    public String getGamesPlayedForLoggedInUser() {
        return "45";
    }

    public double getWinrateQuotaForLoggedInUser() {
        return 0.45;
    }

    public String getWinratePercentageForLoggedInUser() {
        return "40" + "%";
    }
}
