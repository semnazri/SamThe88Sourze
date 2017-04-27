package com.konekthing.intravest.model;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/21/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class SubIndustries {

    private String id, SubIndustries;

    public SubIndustries(){}

    public SubIndustries(String id, String SubIndustries){
        this.id = id;
        this.SubIndustries = SubIndustries;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSubIndustries(String subIndustries) {
        SubIndustries = subIndustries;
    }

    public String getSubIndustries() {
        return SubIndustries;
    }
}
