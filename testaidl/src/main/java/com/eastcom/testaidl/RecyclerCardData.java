package com.eastcom.testaidl;

import android.content.Context;

/**
 * Created by rockgarden on 15/11/3.
 */
public class RecyclerCardData {
    public String name;

    public String picName;

    public RecyclerCardData(String name, String picName)
    {
        this.name = name;
        this.picName = picName;
    }

    public int getImageResourceId( Context context )
    {
        try
        {
            return context.getResources().getIdentifier(this.picName, "drawable", context.getPackageName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
