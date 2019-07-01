package com.codester.fitness.workout.Utils;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DB_PATH;
    public final Context context;
    public SQLiteDatabase database;
    public static String DB_NAME = "workOutData.db";
    public SQLiteDatabase getDb() {
        return this.database;
    }
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = String.format("//data//data//%s//databases//", new Object[]{context.getPackageName()});
        openDataBase();
    }
    public void createDataBase() {
        if (checkDataBase()) {
            Log.i(getClass().toString(), "Database already exists");
            return;
        }
        getReadableDatabase();
        try {
            copyDataBase();
        } catch (IOException e) {
            Log.e(getClass().toString(), "Copying error");
            throw new Error("Error copying database!");
        }
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
        } catch (SQLException e) {
            Log.e(getClass().toString(), "Error while checking db");
        }
        if (checkDb != null) {
            checkDb.close();
        }
        if (checkDb != null) {
            return true;
        }
        return false;
    }
    private void copyDataBase() throws IOException {
        InputStream externalDbStream = this.context.getAssets().open(DB_NAME);
        OutputStream localDbStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = externalDbStream.read(buffer);
            if (bytesRead > 0) {
                localDbStream.write(buffer, 0, bytesRead);
            } else {
                localDbStream.close();
                externalDbStream.close();
                return;
            }
        }
    }
    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (this.database == null) {
            createDataBase();
            this.database = SQLiteDatabase.openDatabase(path, null, 0);
        }
        return this.database;
    }
    public synchronized void close() {
        if (this.database != null) {
            this.database.close();
        }
        super.close();
    }
    public void onCreate(SQLiteDatabase db) {
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
/*
public class DataBaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.redixbit.dictinory/databases/";

	private static String DB_NAME = "Database_Hindi.sqlite";

	private SQLiteDatabase myDataBase;

	private final Context myContext;
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	public void createDataBase() throws IOException {
		this.getReadableDatabase();

		try {
			copyDataBase();

		} catch (IOException e) {

			throw new Error(e.fillInStackTrace());

		}
	}
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			Log.d("path", "" + myPath);
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
			Log.d("DB", "" + checkDB);
		} catch (SQLiteException e) {

			e.printStackTrace();
		}
		if (checkDB != null) {

			checkDB.close();
		}
		return checkDB != null ? true : false;
	}
	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		return super.getWritableDatabase();
	}
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.NO_LOCALIZED_COLLATORS);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}*/