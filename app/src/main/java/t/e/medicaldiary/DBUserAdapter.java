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

    static final String KEY_ROWID = "_id";
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    static final String TAG = "DBUserAdapter";

    static final String DATABASE_NAME = "MedicalDiary";
    static final String DATABASE_TABLE = "users";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table users (_id integer primary key autoincrement, "
            + "username text not null, password text not null);";

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
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
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
            + DATABASE_TABLE
            + " ("+KEY_USERNAME+" , "+KEY_PASSWORD+")"
            + "VALUES ('"+username+"' , '"+password+"');");
    }

    //-----returs all users----

    public Cursor getAllUsers(){

        return db.rawQuery("SELECT * FROM " + DATABASE_TABLE , null);

    }

    public String checkUserAndPassword(String username){

        db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, null, KEY_USERNAME + "=?", new String[]{username},
                null, null, null);

        if(cursor.getCount() < 1){ //User doesn´ exist
            return "USER DOESN´T EXIST";
        }else {
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            return password;
        }


    }
}
