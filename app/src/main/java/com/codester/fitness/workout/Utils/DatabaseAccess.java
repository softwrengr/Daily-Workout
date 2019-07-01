package com.codester.fitness.workout.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public List<Model> getalldata() {
        Cursor cur = database.rawQuery("select * from tips" , null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.easy = cur.getString(cur.getColumnIndex("easy"));
                    obj.medium = cur.getString(cur.getColumnIndex("medium"));
                    obj.hard = cur.getString(cur.getColumnIndex("difficulty"));
                    obj.desc = cur.getString(cur.getColumnIndex("description"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<Model> getRef_id(String id) {
        Cursor cur = database.rawQuery("select * from new_data where ref_id = " + id + "", null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.ref_img = cur.getString(cur.getColumnIndex("image"));
                    obj.ref_id = cur.getString(cur.getColumnIndex("ref_id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }

    public List<Model> getIdData(String strId) {
        Cursor cur = database.rawQuery("SELECT * FROM tips WHERE id =" + strId + "", null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.easy = cur.getString(cur.getColumnIndex("easy"));
                    obj.medium = cur.getString(cur.getColumnIndex("medium"));
                    obj.hard = cur.getString(cur.getColumnIndex("difficulty"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.desc = cur.getString(cur.getColumnIndex("description"));


                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }

    public List<Model> getInsertData() {
        Cursor cur = database.rawQuery("select * from insert_task", null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.date = cur.getString(cur.getColumnIndex("date"));
                    obj.ref_id = cur.getString(cur.getColumnIndex("ref_id"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<Model> getRandomData() {
        Cursor cur = database.rawQuery("select * from tips", null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.easy = cur.getString(cur.getColumnIndex("easy"));
                    obj.medium = cur.getString(cur.getColumnIndex("medium"));
                    obj.hard = cur.getString(cur.getColumnIndex("difficulty"));
                    obj.desc = cur.getString(cur.getColumnIndex("description"));


                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }



//    public List<Model> getRandomData() {
//        Cursor cur = database.rawQuery("select * from tips GROUP BY id ORDER BY Random() LIMIT 10", null);
//        List<Model> item_data = new ArrayList<>();
//        if (cur.getCount() != 0) {
//            if (cur.moveToFirst()) {
//                do {
//                    Model obj = new Model();
//
//                    obj.id = cur.getString(cur.getColumnIndex("id"));
//                    obj.name = cur.getString(cur.getColumnIndex("name"));
//                    obj.image = cur.getString(cur.getColumnIndex("image"));
//                    obj.easy = cur.getString(cur.getColumnIndex("easy"));
//                    obj.medium = cur.getString(cur.getColumnIndex("medium"));
//                    obj.hard = cur.getString(cur.getColumnIndex("difficulty"));
//                    obj.desc = cur.getString(cur.getColumnIndex("description"));
//                    obj.time = cur.getString(cur.getColumnIndex("time"));
//
//
//                    item_data.add(obj);
//                } while (cur.moveToNext());
//            }
//        }
//        cur.close();
//        return item_data;
//    }


    public List<Model> getUpdateTime(String time, String id) {
        Cursor cur = database.rawQuery("UPDATE tips SET time ="+time+" where id = "+id, null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.easy = cur.getString(cur.getColumnIndex("easy"));
                    obj.medium = cur.getString(cur.getColumnIndex("medium"));
                    obj.hard = cur.getString(cur.getColumnIndex("difficulty"));
                    obj.desc = cur.getString(cur.getColumnIndex("description"));


                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<Model> getDateData(String data) {
        Cursor cur = database.rawQuery("select * from insert_task where date = '" + data + "';", null);
        List<Model> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.date = cur.getString(cur.getColumnIndex("date"));
                    obj.ref_id = cur.getString(cur.getColumnIndex("ref_id"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


}
