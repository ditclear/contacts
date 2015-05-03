package vienan.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 2015/4/22.
 */
public class MyDB extends SQLiteOpenHelper {
    private static String DB_NAME="MY_DB.db";//数据库名称
    private static int DB_VERSION=1;//版本号
    private SQLiteDatabase db;//数据库操作对象
    public MyDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=getWritableDatabase();
    }
    private SQLiteDatabase openConnection(){
         return db=getWritableDatabase();
    }
    public void closeConnection(){
        db.close();
    }
    public boolean createTable(String createTableSql){

        try {
            openConnection();
            db.execSQL(createTableSql);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public boolean save(String tableName,ContentValues contentValues){
        try {
            openConnection();
            Log.i("db",tableName);
            db.insert(tableName,null,contentValues);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public boolean update(String tableName,ContentValues contentValues,
                          String whereClause,String[] whereArgs){
        try {
            openConnection();
            db.update(tableName,contentValues,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public boolean delete(String tableName,String whereClause,String[] whereArgs){
        try {
            openConnection();
            db.delete(tableName,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public Cursor find(String findSql,String[] selectArgs){
        Cursor cursor=null;
        System.out.print(findSql+"-----"+selectArgs);
        openConnection();
            cursor=db.rawQuery(findSql,selectArgs);
        return cursor;
    }
    public boolean isTableExists(String tableName){
        String sql="select count(*) from "+tableName;
        try {
            openConnection();
            db.rawQuery(sql,null).close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
