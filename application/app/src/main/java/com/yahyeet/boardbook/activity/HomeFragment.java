package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.HomePresenter;

public class HomeFragment extends Fragment implements IHomeFragment{


    HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homePresenter = new HomePresenter(this);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    public void enableMatchFeed(){

    }




}
