package com.squaresdevelopers.fitness.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    Cursor cur = null;

    public DataManager(Context context) {
        myDbHelper = new DataBaseHelper(context);
        myDbHelper.createDataBase();
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        db = myDbHelper.getReadableDatabase();
    }


    public List<Model> getalldata() {
        Cursor cur = db.rawQuery("select * from tips", null);
        List<Model> item_data = new ArrayList<Model>();
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
        return item_data;
    }




	public List<Model> getRef_id(String id) {
		Cursor cur = db.rawQuery("select * from new_data where ref_id = "+id+"", null);
		List< Model> item_data = new ArrayList<Model>();
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
		return item_data;
	}

    public List<Model> getIdData(String strId) {


        Cursor cur = db.rawQuery("SELECT * FROM tips WHERE id =" + strId + "", null);
        List<Model> item_data = new ArrayList<Model>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Model obj = new Model();

                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.name = cur.getString(cur.getColumnIndex("name"));
                    obj.image = cur.getString(cur.getColumnIndex("image"));
                    obj.desc = cur.getString(cur.getColumnIndex("description"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public List<Model> getInsertData() {
        Cursor cur = db.rawQuery("select * from insert_task", null);
        List<Model> item_data = new ArrayList<Model>();
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
        return item_data;
    }

    public List<Model> getRandomData() {
        Cursor cur = db.rawQuery("select * from tips GROUP BY id ORDER BY Random() LIMIT 10", null);
        List<Model> item_data = new ArrayList<Model>();
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
        return item_data;
    }


    public List<Model> getDateData(String data) {
        Cursor cur = db.rawQuery("select * from insert_task where date = '"+data+"';", null);
        List<Model> item_data = new ArrayList<Model>();
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
        return item_data;
    }

}
