package com.yahyeet.boardbook.model;

public final class Boardbook {


    private static Boardbook instance;

    private Boardbook() {}

    public static Boardbook getInstance(){
        if(instance == null){
            instance = new Boardbook();
        }
        return instance;
    }
}
