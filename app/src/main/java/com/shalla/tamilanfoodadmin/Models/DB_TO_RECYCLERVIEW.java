package com.shalla.tamilanfoodadmin.Models;

public class DB_TO_RECYCLERVIEW {

String  descrption,category,title,reciepe_image_url,foodID;

    public DB_TO_RECYCLERVIEW(String descrption, String category, String title, String reciepe_image_url, String foodID) {
        this.descrption = descrption;
        this.category = category;
        this.title = title;
        this.reciepe_image_url = reciepe_image_url;
        this.foodID = foodID;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReciepe_image_url() {
        return reciepe_image_url;
    }

    public void setReciepe_image_url(String reciepe_image_url) {
        this.reciepe_image_url = reciepe_image_url;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    DB_TO_RECYCLERVIEW() {
        //empty constructor needed
    }


}

