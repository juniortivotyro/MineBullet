package com.cdhoff.MineBullet;

public class Translator {

    public double version = 0;


    public void translate(int i){
        if(i == 340 || i == 338 || i == 335){
            version = 1.12;
        }
        else if(i == 316 || i == 315){
            version = 1.11;
        }else if(i == 210){
            version = 1.10;
        }else if(i == 110 || i == 109 || i == 108 || i == 107){
            version = 1.9;
        }else if(i == 47){
            version = 1.8;
        }else if(i == 5 || i == 4){
            version = 1.7;
        }
    }
}
