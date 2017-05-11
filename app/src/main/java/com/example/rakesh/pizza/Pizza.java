package com.example.rakesh.pizza;

/**
 * Created by Rakesh on 26-02-2017.
 */

public class Pizza {
    private String name;
    private int imageId;
    public static final Pizza[] pizzas={
            new Pizza("Veg Pizza",R.drawable.veg),
            new Pizza("Non Veg Pizza",R.drawable.non_veg),
            new Pizza("Paneer Pizza",R.drawable.paneer)
    };
    private Pizza(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
