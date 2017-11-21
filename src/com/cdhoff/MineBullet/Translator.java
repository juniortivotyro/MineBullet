package com.cdhoff.MineBullet;

public class Translator {



    public static String translate(int i){
        String version = "Unknown";

        if(i == 340){
            version = "1.12.2";
        }else if(i == 338){
            version = "1.12.1";
        }else if(i == 335){
            version = "1.12";
        }
        else if(i == 316){
            version = "1.11.1";
        }else if(i == 315){
            version = "1.11";
        }else if(i == 210){
            version = "1.10";
        }else if(i == 110){
            version = "1.9.4";
        }else if(i == 109){
            version = "1.9.2";
        }else if(i == 108){
            version = "1.9.1";
        }else if(i == 107){
            version = "1.9";
        }else if(i == 47){
            version = "1.8";
        }else if(i == 5){
            version = "1.7.10";
        }else if(i == 4){
            version = "1.7.5";
        }

        return version;
    }
}
