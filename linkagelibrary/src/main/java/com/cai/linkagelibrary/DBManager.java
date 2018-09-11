package com.cai.linkagelibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "city_cn.s3db";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath();
    private SQLiteDatabase database;
    private Context context;
    private File file=null;
    
    DBManager(Context context) {
    	Log.e("scl", "DBManager");
        this.context = context;
    }
 
    public void openDatabase() {
    	Log.e("scl", "openDatabase()");
        this.database = this.openDatabase(DB_PATH  + "/"+ context.getPackageName() + "/" + DB_NAME);
    }
    public SQLiteDatabase getDatabase(){
    	Log.e("scl", "getDatabase()");
    	return this.database;
    }
 
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
        	Log.e("scl", "open and return");
        	file = new File(dbfile);
            if (!file.exists()) {
            	Log.e("scl", "file");
            	InputStream is = context.getResources().openRawResource(R.raw.city);
            	if(is!=null){
            		Log.e("scl", "is null");
            	}else{
            	}
            	FileOutputStream fos = new FileOutputStream(dbfile);
            	if(is!=null){
            		Log.e("scl", "fosnull");
            	}else{
            	}
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count =is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                		Log.e("scl", "while");
                	fos.flush();
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return database;
        } catch (FileNotFoundException e) {
            Log.e("scl", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("scl", "IO exception");
            e.printStackTrace();
        } catch (Exception e){
        	Log.e("scl", "exception "+e.toString());
        }
        return null;
    }
    public void closeDatabase() {
    	Log.e("scl", "closeDatabase()");
    	if(this.database!=null)
    		this.database.close();
    }
}