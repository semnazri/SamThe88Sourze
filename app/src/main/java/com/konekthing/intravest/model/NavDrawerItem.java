package com.konekthing.intravest.model;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 1/2/17.
 *
 * @copyright 2017
 *
 */
public class NavDrawerItem {

    private boolean showNotify;
    private String title;
//    private Boolean isheader = false;



    private int icon;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }
    public boolean getShowNotify(){
        return this.showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
