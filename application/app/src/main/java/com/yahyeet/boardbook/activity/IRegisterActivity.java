package com.yahyeet.boardbook.activity;

import com.yahyeet.boardbook.presenter.RegisterPresenter;

public interface IRegisterActivity {

    RegisterPresenter getRegisterPresenter();

    void setRegisterPresenter(RegisterPresenter registerPresenter);

    void finish();
}
