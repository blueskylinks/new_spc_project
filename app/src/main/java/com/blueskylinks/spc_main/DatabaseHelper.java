package com.blueskylinks.spc_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "spcmotor.db";
    public static final String TABLE_NAME = "motor_status";
    public static final String TABLE_NAME1 = "motor_set";
    public static final String TABLE_MSG1 = "table_msg";
    public static final String COL_1 = "PH_ID";
    public static final String COL_2 = "PH_PASS";
    public static final String COL_3 = "V1";
    public static final String COL_4 = "V2";
    public static final String COL_5 = "V3";
    public static final String COL_6 = "C1";
    public static final String COL_7 = "C2";
    public static final String COL_8 = "C3";
    public static final String COL_9 = "MOT_ST";
    public static final String COL_10 = "PHASE";
    public static final String COL_11 = "CTL_BY";
    public static final String COL_12 = "CTL_ST";
    public static final String COL_13 = "CTL_DATE";
    public static final String COL_14 = "CTL_TIME";
    public static final String COL_15 = "TOWER_SIG";
    public static final String COL_16 = "SYNC_DATE";

    public static final String COL_SET1 = "PH_ID";
    public static final String COL_SET2 = "RTC_ST";
    public static final String COL_SET3 = "RTC_1_1";
    public static final String COL_SET4 = "RTC_1_2";
    public static final String COL_SET5 = "RTC_2_1";
    public static final String COL_SET6 = "RTC_2_2";
    public static final String COL_SET7 = "RTC_3_1";
    public static final String COL_SET8 = "RTC_3_2";
    public static final String COL_SET9 = "EXT_CH";
    public static final String COL_SET10 = "SINGLE_PH";
    public static final String COL_SET11 = "FEED_SMS";
    public static final String COL_SET12 = "FED_CALL";
    public static final String COL_SET13 = "STOLEN_PR";

    public static final String COL_MSG1 = "PH_ID";
    public static final String COL_MSG2 = "MSG";
    public static final String COL_MSG3 = "MSG_DATE";
    public static final String COL_MSG4 = "MSG_LOCDATE";
    public static final String COL_MSG5 = "USER";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (PH_ID TEXT,PH_PASS TEXT,V1 TEXT,V2 TEXT,V3 TEXT,C1 TEXT,C2 TEXT,C3 TEXT,MOT_ST TEXT,PHASE TEXT, CTL_BY TEXT, CTL_ST TEXT, CTL_DATE TEXT, CTL_TIME TEXT, TOWER_SIG TEXT, SYNC_DATE TEXT)");
        db.execSQL("create table " + TABLE_NAME1 +" (PH_ID TEXT, RTC_ST TEXT, RTC_1_1 TEXT, RTC_1_2 TEXT, RTC_2_1 TEXT, RTC_2_2 TEXT, RTC_3_1 TEXT, RTC_3_2 TEXT, " +
                "EXT_CH TEXT, SINGLE_PH TEXT, REV_PH TEXT, FEED_SMS TEXT, FED_CALL TEXT, STOLEN_PR TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_MSG1 +"(PH_ID TEXT, MSG TEXT, MSG_DATE TEXT, MSG_LOCDATE TEXT, USER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MSG1);
        onCreate(db);
    }

    public boolean insertData(String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PH_ID",ph_no);
        contentValues.put(COL_2,"");
        contentValues.put(COL_3,"");
        contentValues.put(COL_4,"");
        contentValues.put(COL_5,"");
        contentValues.put(COL_6,"");
        contentValues.put(COL_7,"");
        contentValues.put(COL_8,"");
        contentValues.put(COL_9,"");
        contentValues.put(COL_10,"");
        contentValues.put(COL_11,"");
        contentValues.put(COL_12,"");
        contentValues.put(COL_13,"");
        contentValues.put(COL_14,"");
        contentValues.put(COL_15,"");
        contentValues.put(COL_16,"");
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insert_setting_tb(String ph_no, String tab_col) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(tab_col,ph_no);
        contentValues.put(COL_SET2,"");
        contentValues.put(COL_SET3,"");
        contentValues.put(COL_SET4,"");
        contentValues.put(COL_SET5,"");
        contentValues.put(COL_SET6,"");
        contentValues.put(COL_SET7,"");
        contentValues.put(COL_SET8,"");
        contentValues.put(COL_SET9,"");
        contentValues.put(COL_SET10,"");
        contentValues.put(COL_SET11,"");
        contentValues.put(COL_SET12,"");
        contentValues.put(COL_SET13,"");
        long result = db.insert(TABLE_NAME1,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insert_msg_tb(String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SET1,ph_no);
        contentValues.put(COL_SET2,"");
        contentValues.put(COL_SET3,"");
        contentValues.put(COL_SET4,"");
        contentValues.put(COL_SET5,"");
        long result = db.insert(TABLE_MSG1,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s_temp=ph_no.toString();
        Log.i("phone_id", s_temp);
        Cursor res1 = db.rawQuery("SELECT PH_ID FROM motor_status",null);
        Log.i("database rec count", String.valueOf(res1.getCount()));
        Cursor res = db.rawQuery("SELECT * FROM motor_status WHERE PH_ID="+"'"+s_temp+"'",null);
        return res;
    }

    public Cursor get_date(String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s_temp=ph_no.toString();
        Cursor res = db.rawQuery("SELECT CTL_DATE FROM motor_status WHERE PH_ID="+"'"+s_temp+"'",null);
        return res;
    }

    public Cursor getAllData_set(String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s_temp=ph_no.toString();
        Log.i("phone_id", s_temp);
        Cursor res1 = db.rawQuery("SELECT PH_ID FROM motor_set",null);
        Log.i("database rec count", String.valueOf(res1.getCount()));
        Cursor res = db.rawQuery("SELECT * FROM motor_set WHERE PH_ID="+"'"+s_temp+"'",null);
        return res;
    }

    public boolean updateData(String[] mot_data, String ph_no, int code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch(code) {
            case 1025:
                contentValues.put(COL_3, mot_data[2]);
                contentValues.put(COL_4, mot_data[3]);
                contentValues.put(COL_5, mot_data[4]);
                contentValues.put(COL_6, mot_data[5]);
                contentValues.put(COL_7, mot_data[6]);
                contentValues.put(COL_8, mot_data[7]);
                contentValues.put(COL_9, mot_data[8]);
                contentValues.put(COL_10, mot_data[9]);
                contentValues.put(COL_16, mot_data[15]);
                Date d1 = new Date();
                contentValues.put(COL_13,String.valueOf(d1.getTime()));
                Log.i("Date-D1",String.valueOf(d1.getTime()));
                break;
            case 1024:
                contentValues.put(COL_9, mot_data[1]);
                Date d2 = new Date();
                contentValues.put(COL_13,String.valueOf(d2.getTime()));
                Log.i("Date-D222",String.valueOf(d2.getTime()));
                break;
            case 1028:
                contentValues.put(COL_9, mot_data[8]);
                Date d3 = new Date();
                contentValues.put(COL_13,String.valueOf(d3.getTime()));
                Log.i("Date-D222",String.valueOf(d3.getTime()));
                break;
            case 1004:
                contentValues.put(COL_10, mot_data[0]);
                Date d4 = new Date();
                contentValues.put(COL_13,String.valueOf(d4.getTime()));
                Log.i("Date-D222",String.valueOf(d4.getTime()));
                break;
            case 1005:
                contentValues.put(COL_10, mot_data[1]);
                Date d5 = new Date();
                contentValues.put(COL_13,String.valueOf(d5.getTime()));
                Log.i("Date-D222",String.valueOf(d5.getTime()));
            default:
                break;
        }
        db.update(TABLE_NAME, contentValues, "PH_ID=?",new String[] { ph_no });
        return true;
    }

    public boolean update_set(String[] mot_data, String ph_no, int code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch(code) {
            case 2701:
                contentValues.put(COL_SET2, mot_data[1]);
                break;
            case 2702:
                contentValues.put(COL_SET2, mot_data[1]);
                contentValues.put(COL_SET3, mot_data[2]);
                contentValues.put(COL_SET4, mot_data[3]);
                contentValues.put(COL_SET5, mot_data[4]);
                contentValues.put(COL_SET6, mot_data[5]);
                contentValues.put(COL_SET7, mot_data[6]);
                contentValues.put(COL_SET8, mot_data[7]);
                break;
            case 2703:
                contentValues.put(COL_SET3, mot_data[2]);
                contentValues.put(COL_SET4, mot_data[3]);
                contentValues.put(COL_SET5, mot_data[4]);
                contentValues.put(COL_SET6, mot_data[5]);
                contentValues.put(COL_SET7, mot_data[6]);
                contentValues.put(COL_SET8, mot_data[7]);
            case 2704:
                contentValues.put(COL_SET3, mot_data[2]);
                contentValues.put(COL_SET4, mot_data[3]);
                contentValues.put(COL_SET5, mot_data[4]);
                contentValues.put(COL_SET6, mot_data[5]);
                contentValues.put(COL_SET7, mot_data[6]);
                contentValues.put(COL_SET8, mot_data[7]);
                break;
            case 2711:
                contentValues.put(COL_SET9, mot_data[1]);
                Log.i("Data Setting","Updated...");
            default:
                break;
        }
        db.update(TABLE_NAME1, contentValues, "PH_ID=?",new String[] { ph_no });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}