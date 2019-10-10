package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.CreateingMatches.ICreateMatchActivity;

public class CreateMatchPresenter {

    private ICreateMatchActivity ma;

   public CreateMatchPresenter(ICreateMatchActivity ma){
       this.ma = ma;
   }
}
