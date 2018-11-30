package com.sunland.hzhc.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * get data from file xxx.db in directory assets, store data into internal storage, then read data from internal
 * storage.
 */
public class AssetDBReader {

    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;
    public AssetDBReader(Context context){
        this.mContext=context;
    }

    public SQLiteDatabase readAssetDb(String dirPath, String dbName) {
        File db_file=new File(dirPath,dbName);
        Log.d("info",db_file.getAbsolutePath());
        if(db_file.exists()){
            mSqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(db_file,null);

        }else {
            try{
                InputStream is=mContext.getAssets().open(dbName);
                FileOutputStream fos=new FileOutputStream(db_file);
                byte[] bytes=new byte[1024];
                int count=0;
                while((count=is.read(bytes))>0){
                    fos.write(bytes,0,count);
                }
                fos.flush();
                fos.close();
                is.close();

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            mSqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(db_file,null);
        }
        return mSqLiteDatabase;
    }

    public SQLiteDatabase getDatabase(){
        if(mSqLiteDatabase!=null){
            return mSqLiteDatabase;
        }else {
            return null;
        }
    }

}
