package t.e.medicaldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanja on 20/02/2018.
 */

public class DBUserAdapter {


    static final String DATABASE_NAME = "MedicalDiary";

    //tables
    static final String TABLE_USERS = "users";
    static final String TABLE_MEDICINS = "medicins";
    static final String TABLE_USERS_MEDICINS = "users_medicin";
    static final String TABLE_MEDICIN_TAKEN = "medicin_taken";

    //common colum names
    static final String KEY_ID = "_id";

    //Table user colum names
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";

    //Table medicins colum names
    static final String KEY_MEDICIN = "medicin";
    static final String KEY_DOSAGE = "dosage";
    static final String KEY_IMAGE_PATH = "image_path";

    //Table users_medicins colum names
    static final String KEY_USERS_ID = "users_id";
    static final String KEY_MEDICINS_ID = "medicins_id";

    //Table medicin_taken column names
    static final String KEY_DATE = "date";



    static final int DATABASE_VERSION = 12;
    static final String TAG = "DBUserAdapter";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
            + " TEXT NOT NULL," + KEY_PASSWORD + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_MEDICINS = "CREATE TABLE "
            + TABLE_MEDICINS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MEDICIN
            + " TEXT NOT NULL," + KEY_DOSAGE + " TEXT NOT NULL," + KEY_IMAGE_PATH + " TEXT" + ")";

    private static final String CREATE_TABLE_USERS_MEDICINS = "CREATE TABLE "
            + TABLE_USERS_MEDICINS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERS_ID
            + " INTEGER," + KEY_MEDICINS_ID + " INTEGER " + ")";

    private static final String CREATE_TABLE_MEDICIN_TAKEN = "CREATE TABLE "
            + TABLE_MEDICIN_TAKEN + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERS_ID
            + " INTEGER," + KEY_MEDICINS_ID + " INTEGER, " + KEY_DATE + " DATE " + ")";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBUserAdapter(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{


        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                db.execSQL(CREATE_TABLE_USERS);
                db.execSQL(CREATE_TABLE_MEDICINS);
                db.execSQL(CREATE_TABLE_USERS_MEDICINS);
                db.execSQL(CREATE_TABLE_MEDICIN_TAKEN);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_MEDICINS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICIN_TAKEN);
            onCreate(db);
        }
    }

    //----opens the database---
    public DBUserAdapter open() throws SQLException{

        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database----

    public void close(){

        DBHelper.close();
    }

    //-----inserts a user--------

    public void insertUser(String username, String password){

        db.execSQL("INSERT INTO "
            + TABLE_USERS
            + " ("+KEY_USERNAME+" , "+KEY_PASSWORD+")"
            + "VALUES ('"+username+"' , '"+password+"');");

        //Fetch user id from database and set user info to User-class
        Cursor cursor = db.rawQuery("SELECT _id FROM users WHERE username=?", new String[]{username});
        if(cursor != null){
            cursor.moveToFirst();
            String id_fetched = cursor.getString(0);
            User user = new User();
            user.setId(id_fetched);
            user.setUsername(username);
            user.setPassword(password);
        }


    }

    //-----returs all users----

    public Cursor getAllUsers(){

        return db.rawQuery("SELECT * FROM " + TABLE_USERS , null);

    }

    public String checkUserAndPassword(String username){

        db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, KEY_USERNAME + "=?", new String[]{username},
                null, null, null);

        if(cursor.getCount() < 1){ //User doesn´ exist
            return "USER DOESN´T EXIST";
        }else {
            cursor.moveToFirst();
            String id_fetched = cursor.getString(cursor.getColumnIndex(KEY_ID));
            String username_fetched = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            String password_fetched = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            User user = new User();
            user.setId(id_fetched);
            user.setUsername(username_fetched);
            user.setPassword(password_fetched);
            return password_fetched;
        }


    }

    public void putMedicin(Medicin medicin){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MEDICIN, medicin.getMedicin_name());
        contentValues.put(KEY_DOSAGE, medicin.getDosage());
        contentValues.put(KEY_IMAGE_PATH, medicin.getImagePath());

        db.insert(TABLE_MEDICINS, null, contentValues);

    }

    public void insertMedicin(String medicin_name, String medicin_dosage){
        db.execSQL("INSERT INTO "
                + TABLE_MEDICINS
                + " ("+KEY_MEDICIN+" , "+KEY_DOSAGE+")"
                + "VALUES ('"+ medicin_name +"' , '"+ medicin_dosage +"');");

        //Fetch medicin id from database and set medicin info to Medicin-class
        Cursor cursor = db.rawQuery("SELECT _id FROM medicins WHERE medicin=?", new String[]{medicin_name});
        if(cursor != null){
            cursor.moveToFirst();
            String id_fetched = cursor.getString(0);
            cursor.close();

            Medicin medicin = new Medicin();
            medicin.setId(id_fetched);
            medicin.setMedicin_name(medicin_name);
            medicin.setDosage(medicin_dosage);
            createUserMedicin();
        }

    }

    public void createUserMedicin() {

        User user = new User();
        Medicin medicin = new Medicin();
        String user_id = user.getId();
        String medicin_id = medicin.getId();
        db.execSQL("INSERT INTO "
                + TABLE_USERS_MEDICINS
                + " ("+KEY_USERS_ID+" , "+KEY_MEDICINS_ID+")"
                + "VALUES ('"+ user_id +"' , '"+ medicin_id +"');");

    }

    public Cursor getAllMedicins(){

        return db.rawQuery("SELECT * FROM " + TABLE_MEDICINS , null);

    }

    public void insertMedicinTaken(String userId, String medicinID, String date){
        db.execSQL("INSERT INTO "
                + TABLE_MEDICIN_TAKEN
                + " ("+KEY_USERS_ID+" , "+KEY_MEDICINS_ID+" , "+KEY_DATE+" )"
                + "VALUES ('"+ userId +"' , '"+ medicinID +"' , '"+date+"');");
    }

    public String getMedId(String medicin_name){
        Cursor cursor = db.rawQuery("SELECT _id FROM medicins WHERE medicin=?", new String[]{medicin_name});
        if(cursor != null){
            cursor.moveToFirst();
            String id_fetched = cursor.getString(0);
            cursor.close();
            return id_fetched;
        }
        else {
            return "notfound";
        }
    }

    public Cursor getAllTakenMedicins(String userId){
        db = DBHelper.getReadableDatabase();
        /*Cursor cursor = db.query(TABLE_MEDICIN_TAKEN, null, KEY_USERS_ID + "=?", new String[]{userId},
                null, null, null);
        return cursor;*/
        Cursor cursor = db.rawQuery("SELECT date, medicin FROM "  + TABLE_MEDICINS +
        " tm, " + TABLE_MEDICIN_TAKEN + " mt WHERE mt." + KEY_USERS_ID + " = '" + userId + "'" +
        " AND tm." + KEY_ID + " = " + "mt." + KEY_MEDICINS_ID + " ORDER BY date DESC", null);
        return cursor;
    }

    // Getting All Children
    public List<Medicin> getAllMedicins(String userId) {
        List<Medicin> listOfMedicins = new ArrayList<Medicin>();
        // Select All Query
        String selectQuery = "SELECT medicin, date FROM "  + TABLE_MEDICINS +
                " tm, " + TABLE_MEDICIN_TAKEN + " mt WHERE mt." + KEY_USERS_ID + " = '" + userId + "'" +
                " AND tm." + KEY_ID + " = " + "mt." + KEY_MEDICINS_ID + " ORDER BY date DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicin medicin = new Medicin();
                //medicin.setId(cursor.getString(0));
                medicin.setMedicin_name(cursor.getString(0));
                medicin.setDate_given(cursor.getString(1));

                // Adding child to list
                listOfMedicins.add(medicin);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listOfMedicins;
    }
}
