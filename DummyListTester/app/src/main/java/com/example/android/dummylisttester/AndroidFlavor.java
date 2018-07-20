package com.example.android.dummylisttester;

import static android.R.attr.id;

/**
 * Created by Operator on 10/17/2016.
 */

public class AndroidFlavor {
    private int ImageID;
    private String Name;
    private String Number;

    public AndroidFlavor(String name, String number, int id){
        ImageID = id;
        Name = name;
        Number = number;
    }

    public int getImageID() {
        return ImageID;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }
}
