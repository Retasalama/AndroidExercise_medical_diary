package t.e.medicaldiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;

/**
 * Created by tanja on 23/02/2018.
 */

public class Medicin {

    private String id;
    private String medicin_name;
    private String date_given;
    private String dosage;
    private String imagePath;

    //constructors

    public Medicin(){}

    public Medicin(String id, String medicin_name, String date_given, String imagePath){
        this.id = id;
        this.medicin_name = medicin_name;
        this.date_given = date_given;
        this.imagePath = imagePath;
    }

    /*public Medicin(int id, String medicin_name, String dosage){
        this.id = id;
        this.medicin_name = medicin_name;
        this.date_given = dosage;
    }*/

    public void setId(String id){
        this.id = id;
    }

    public void setMedicin_name(String medicin_name){
        this.medicin_name = medicin_name;
    }

    public void setDosage(String dosage){
        this.dosage = dosage;
    }

    public void setDate_given(String date_given){
        this.date_given = date_given;
    }

    public void setPhotoPath(String imagePath){this.imagePath = imagePath;}

    public String  getId(){
        return id;
    }

    public String getMedicin_name(){
        return medicin_name;
    }

    public String getDosage(){
        return dosage;
    }
    public String getDate_given(){
        return date_given;
    }
    public String getImagePath(){return imagePath;}

}




