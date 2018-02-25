package t.e.medicaldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tanja on 20/02/2018.
 */

public class DBUserAdapter {


    static final String DATABASE_NAME = "MedicalDiary";

    //tables
    static final String TABLE_USERS = "users";
    static final String TABLE_MEDICINS = "medicins";
    static final String TABLE_USERS_MEDICINS = "users_medicin";

    //common colum names
    static final String KEY_ID = "_id";

    //Table user colum names
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";

    //Table medicins colum names
    static final String KEY_MEDICIN = "medicin";
    static final String KEY_DOSAGE = "dosage";

    //Table users_medicins colum names
    static final String KEY_USERS_ID = "users_id";
    static final String KEY_MEDICINS_ID = "medicins_id";



    static final int DATABASE_VERSION = 4;
    static final String TAG = "DBUserAdapter";


   /* private static final String CREATE_TABLE_USERS = "create table users (_id integer primary key autoincrement, "
            + "username text not null, password text not null);";*/

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
            + " TEXT NOT NULL," + KEY_PASSWORD + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_MEDICINS = "CREATE TABLE "
            + TABLE_MEDICINS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MEDICIN
            + " TEXT NOT NULL," + KEY_DOSAGE + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_USERS_MEDICINS = "CREATE TABLE "
            + TABLE_USERS_MEDICINS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERS_ID
            + " INTEGER," + KEY_MEDICINS_ID + " INTEGER " + ")";

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
}
