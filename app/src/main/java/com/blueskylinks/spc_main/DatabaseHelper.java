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
    public static final String COL_17 = "LAST_MSG";

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
    public static final String COL_SET11 = "REV_PH";
    public static final String COL_SET12 = "FEED_SMS";
    public static final String COL_SET13 = "FED_CALL";
    public static final String COL_SET14 = "STOLEN_PR";
    public static final String COL_SET15 = "OVER_LOAD_STATUS";
    public static final String COL_SET16 = "OL_TRIP_TIME";
    public static final String COL_SET17 = "OL_TRIP_C3";
    public static final String COL_SET18 = "OL_TRIP_C2";
    public static final String COL_SET19 = "OL_PERCENT";
    public static final String COL_SET20 = "DRY_RUN_STATUS";
    public static final String COL_SET21 = "DR_TRIPTIME";
    public static final String COL_SET22 = "DR_TRIPC3";
    public static final String COL_SET23 = "DR_TRIPC2";
    public static final String COL_SET24 = "NOLOAD_TRIP_PERCENT";
    public static final String COL_SET25 = "RESTART_DR_STATUS";
    public static final String COL_SET26 = "RESTART_TIME";
    public static final String COL_SET27 = "O_VOLT_PF_STATUS";
    public static final String COL_SET28 = "MAX_VOLT3";
    public static final String COL_SET29 = "MAX_VOLT2";
    public static final String COL_SET30 = "U_VOLT_PF_STATUS";
    public static final String COL_SET31 = "MIN_VOLT3";
    public static final String COL_SET32 = "MIN_VOLT2";
    public static final String COL_SET33 = "V_DIFF_VAL";
    public static final String COL_SET34 = "SPP_V_STATUS";
    public static final String COL_SET35 = "SPP_V_DIFF_VAL";


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
        db.execSQL("create table " + TABLE_NAME +" (PH_ID TEXT,PH_PASS TEXT,V1 TEXT,V2 TEXT,V3 TEXT,C1 TEXT,C2 TEXT,C3 TEXT,MOT_ST TEXT,PHASE TEXT, CTL_BY TEXT, CTL_ST TEXT, CTL_DATE TEXT, CTL_TIME TEXT, TOWER_SIG TEXT, SYNC_DATE TEXT, LAST_MSG TEXT)");
        db.execSQL("create table " + TABLE_NAME1 +" (PH_ID TEXT, RTC_ST TEXT, RTC_1_1 TEXT, RTC_1_2 TEXT, RTC_2_1 TEXT, RTC_2_2 TEXT, RTC_3_1 TEXT, RTC_3_2 TEXT, " +
                "EXT_CH TEXT, SINGLE_PH TEXT, REV_PH TEXT, FEED_SMS TEXT, FED_CALL TEXT, STOLEN_PR TEXT, OVER_LOAD_STATUS TEXT, OL_TRIP_TIME TEXT, OL_TRIP_C3 TEXT, OL_TRIP_C2 TEXT, OL_PERCENT TEXT, DRY_RUN_STATUS TEXT, DR_TRIPTIME TEXT, DR_TRIPC3 TEXT, DR_TRIPC2 TEXT, NOLOAD_TRIP_PERCENT TEXT,RESTART_DR_STATUS TEXT, RESTART_TIME TEXT, O_VOLT_PF_STATUS TEXT, MAX_VOLT3 TEXT, MAX_VOLT2 TEXT, U_VOLT_PF_STATUS TEXT, MIN_VOLT3 TEXT, MIN_VOLT2 TEXT, V_DIFF_VAL TEXT, SPP_V_STATUS TEXT, SPP_V_DIFF_VAL TEXT)");
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
        contentValues.put(COL_SET14,"");
        contentValues.put(COL_SET15,"");
        contentValues.put(COL_SET16,"");
        contentValues.put(COL_SET17,"");
        contentValues.put(COL_SET18,"");
        contentValues.put(COL_SET19,"");
        contentValues.put(COL_SET20,"");
        contentValues.put(COL_SET21,"");
        contentValues.put(COL_SET22,"");
        contentValues.put(COL_SET23,"");
        contentValues.put(COL_SET24,"");
        contentValues.put(COL_SET25,"");
        contentValues.put(COL_SET26,"");
        contentValues.put(COL_SET27,"");
        contentValues.put(COL_SET28,"");
        contentValues.put(COL_SET29,"");
        contentValues.put(COL_SET30,"");
        contentValues.put(COL_SET31,"");
        contentValues.put(COL_SET32,"");
        contentValues.put(COL_SET33,"");
        contentValues.put(COL_SET34,"");
        contentValues.put(COL_SET35,"");

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
        String s_temp=ph_no.toString();
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
                contentValues.put(COL_12, mot_data[11]);
                contentValues.put(COL_16, mot_data[15]);
                Date d1 = new Date();
                contentValues.put(COL_13,String.valueOf(d1.getTime()));
                Log.i("SPC 25",String.valueOf(d1.getTime()));
                break;
            case 1024:
                contentValues.put(COL_9, mot_data[1]);
                Date d2 = new Date();
                contentValues.put(COL_13,String.valueOf(d2.getTime()));
                Log.i("SPC 24",String.valueOf(d2.getTime()));
                break;
            case 1028:
                contentValues.put(COL_9, mot_data[8]);
                Date d3 = new Date();
                contentValues.put(COL_13,String.valueOf(d3.getTime()));
                Log.i("Date-D222",String.valueOf(d3.getTime()));
                break;
            case 1029:
                contentValues.put(COL_9, mot_data[1]);
                contentValues.put(COL_11, mot_data[2]);
                Date d13 = new Date();
                contentValues.put(COL_13,String.valueOf(d13.getTime()));
                Log.i("Date-D222",String.valueOf(d13.getTime()));
                break;
            case 1030:
                contentValues.put(COL_9, mot_data[1]);
                Date d14 = new Date();
                contentValues.put(COL_13,String.valueOf(d14.getTime()));
                Log.i("test........",String.valueOf(d14.getTime()));
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
                Log.i("test........",String.valueOf(d5.getTime()));
                break;
            case 1090:
                contentValues.put(COL_12,mot_data[1]);
                contentValues.put(COL_17,mot_data[3]);
                break;
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
                break;
            case 2712:
                contentValues.put(COL_SET10, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2713:
                contentValues.put(COL_SET11, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2714:
                contentValues.put(COL_SET12, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2715:
                contentValues.put(COL_SET13, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2716:
                contentValues.put(COL_SET14, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2717:
                contentValues.put(COL_SET15, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2718:
                contentValues.put(COL_SET16, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2719:
                contentValues.put(COL_SET17, mot_data[1]);
                contentValues.put(COL_SET18, mot_data[2]);
                Log.i("Data Setting","Updated...");
                break;
            case 2721:
                contentValues.put(COL_SET19, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2722:
                contentValues.put(COL_SET20, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2723:
                contentValues.put(COL_SET21, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2724:
                contentValues.put(COL_SET22, mot_data[1]);
                contentValues.put(COL_SET23, mot_data[2]);
                Log.i("Data Setting","Updated...");
                break;
            case 2726:
                contentValues.put(COL_SET24, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2727:
                contentValues.put(COL_SET25, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2728:
                contentValues.put(COL_SET26, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2729:
                contentValues.put(COL_SET27, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2730:
                contentValues.put(COL_SET28, mot_data[1]);
                contentValues.put(COL_SET29, mot_data[2]);
                Log.i("Data Setting","Updated...");
                break;
            case 2732:
                contentValues.put(COL_SET30, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2733:
                contentValues.put(COL_SET31, mot_data[1]);
                contentValues.put(COL_SET32, mot_data[2]);
                Log.i("Data Setting","Updated...");
                break;
            case 2735:
                contentValues.put(COL_SET33, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2736:
                contentValues.put(COL_SET34, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;
            case 2737:
                contentValues.put(COL_SET35, mot_data[1]);
                Log.i("Data Setting","Updated...");
                break;

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