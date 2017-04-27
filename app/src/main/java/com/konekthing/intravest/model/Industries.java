package com.konekthing.intravest.model;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Industries {

    String Industries_name;
    int img_Industries;
    public Industries(){}

    public Industries(String Industries_name, int img_Industries){

        this.Industries_name = Industries_name;
        this.img_Industries = img_Industries;
    }

    public void setImg_Industries(int img_Industries) {
        this.img_Industries = img_Industries;
    }

    public int getImg_Industries() {
        return img_Industries;
    }

    public void setIndustries_name(String industries_name) {
        Industries_name = industries_name;
    }

    public String getIndustries_name() {
        return Industries_name;
    }
}
