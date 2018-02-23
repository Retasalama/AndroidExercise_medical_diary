/**
 * Created by tanja on 23/02/2018.
 */

public class Medicin {

    int id;
    String medicin_name;
    String dosage;

    //constructors

    public Medicin(){}

    public Medicin(int id, String medicin_name){
        this.id = id;
        this.medicin_name = medicin_name;
    }

    public Medicin(int id, String medicin_name, String dosage){
        this.id = id;
        this.medicin_name = medicin_name;
        this.dosage = dosage;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setMedicin_name(String medicin_name){
        this.medicin_name = medicin_name;
    }

    public void setDosage(String dosage){
        this.dosage = dosage;
    }

    public int getId(){
        return this.id;
    }

    public String getMedicin_name(){
        return this.medicin_name;
    }

    public String getDosage(){
        return this.dosage;
    }
}
