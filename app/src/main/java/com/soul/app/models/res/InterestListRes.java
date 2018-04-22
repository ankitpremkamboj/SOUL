package com.soul.app.models.res;

/**
 * Created by techahead on 18/7/16.
 */
public class InterestListRes {

    /**
     * category_id : 117
     * category_name : Ghazal
     * category_image : http://52.25.82.251/salaam-swipe/assets/category_image/1435034574_64_Ghazal.png
     */

    private String category_id;
    private String category_name;
    private String category_image;
    private boolean selected;

    /**
     * is_primary : 1
     */

    private String is_primary="";

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }



    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getIs_primary() {
        return is_primary;
    }

    public void setIs_primary(String is_primary) {
        this.is_primary = is_primary;
    }
}
