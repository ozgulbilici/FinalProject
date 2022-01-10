package tr.edu.mu.ceng.mad.reminderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "plan_database";
    //Database Table name
    private static final String TABLE_NAME = "plan";
    //Table columns
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NAME = "remindername";
    public static final String NOTE = "remindernote";
    private SQLiteDatabase sqLiteDatabase;

    //creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE + " TEXT NOT NULL,"+TIME+" TEXT NOT NULL,"+NAME+" TEXT NOT NULL,"+NOTE+" TEXT NOT NULL);";

    //Constructor
    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addPlanEarly(Plan plan) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.DATE,plan.getDate() );
        contentValues.put(DatabaseHelper.TIME,plan.getTime() );
        contentValues.put(DatabaseHelper.NAME,plan.getName() );
        contentValues.put(DatabaseHelper.NOTE,plan.getNote() );

        sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null,contentValues);

    }
    public void addPlan(Plan plan) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.DATE,plan.getDate() );
        contentValues.put(DatabaseHelper.TIME,plan.getTime() );
        contentValues.put(DatabaseHelper.NAME,plan.getName() );
        contentValues.put(DatabaseHelper.NOTE,plan.getNote() );

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+" where remindernote ='"+plan.getNote()+"'" ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null || !cursor.moveToFirst()){
            sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null,contentValues);

        }


    }

    public ArrayList<Plan> getAllPlans(){
        ArrayList<Plan> plans = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME;


        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String name = cursor.getString(3);
                String note = cursor.getString(4);


                Plan plan=new Plan(id,date,time,name,note);
                plans.add(plan);


            }while (cursor.moveToNext());
        }
        cursor.close();
        return plans;
    }


    public Cursor VeriGetir(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery, null);


        return cursor;
    }

    public Cursor VeriGetirByID(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+" where ID='"+id+"'" ;
        Cursor cursor = db.rawQuery(selectQuery, null);


        return cursor;
    }


    public int deletePlan(int id) {
        sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});
    }
}