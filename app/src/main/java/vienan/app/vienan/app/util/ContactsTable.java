package vienan.app.vienan.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Vector;

import vienan.app.db.MyDB;

/**
 * Created by lenovo on 2015/4/22.
 */
public class ContactsTable {
    //定义类的成员变量
    private final static String TABLENAME="contactsTable";
    private MyDB db;

    public ContactsTable(Context context) {
        db=new MyDB(context);
        if(!db.isTableExists(TABLENAME)){
            String createTableSql="create table if not exists " +
            TABLENAME+"(DB_id integer primary key autoincrement," +
                    User.NAME+" varchar,"+
                    User.MOBILE+" varchar,"+
                    User.QQ+" varchar,"+
                    User.UNIT+" varchar,"+
                    User.ADDRESS+" varchar)";
            Log.i("sql",createTableSql);
            db.createTable(createTableSql);
        }
    }
    //添加数据
    public boolean addDate(User user){
        ContentValues contentValues=new ContentValues();
        contentValues.put(User.NAME,user.getUsername());
        contentValues.put(User.MOBILE,user.getMobile());
        contentValues.put(User.QQ,user.getQq());
        contentValues.put(User.UNIT,user.getUnit());
        contentValues.put(User.ADDRESS,user.getAddress());
        return db.save(TABLENAME,contentValues);
    }
    public User getUserbyId(int userID){
        Cursor cursor=null;
        String sql = "select * from " + TABLENAME + " where DB_id=?";
        try {
            cursor = db.find(sql, new String[]{userID + ""});
            User user=new User();
            while (cursor.moveToNext()){
                user.setDB_id(cursor.getInt(cursor.getColumnIndex("DB_id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex(User.NAME)));
                user.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                user.setUnit(cursor.getString(cursor.getColumnIndex(User.UNIT)));
                user.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
            }
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }
    public boolean updateUser(User user){
        ContentValues contentValues=new ContentValues();
        contentValues.put(User.NAME,user.getUsername());
        contentValues.put(User.MOBILE,user.getMobile());
        contentValues.put(User.QQ,user.getQq());
        contentValues.put(User.UNIT,user.getUnit());
        contentValues.put(User.ADDRESS,user.getAddress());
        return db.update(TABLENAME,contentValues,"DB_id=?",new String[] {user.getDB_id()+""});
    }
    public boolean deleteByName(User user){
        return db.delete(TABLENAME,"DB_id=?",new String[]{user.getDB_id()+""});
    }
    public User[] findUserByKey(String key){
        Vector<User> users=new Vector<User>();
        Cursor cursor=null;
        try{
            cursor=db.find("select * from "+TABLENAME+" where "+User.NAME+" like '%"+key+"%' or " +
                    User.MOBILE+" like '%"+key+"%' or "+User.QQ+" like '%"+key+"%'"
                    ,null);
            while(cursor.moveToNext()){
                User user=new User();
                user.setDB_id(cursor.getInt(cursor.getColumnIndex("DB_id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex(User.NAME)));
                user.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                user.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                user.setUnit(cursor.getString(cursor.getColumnIndex(User.UNIT)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if(users.size()>0){
            return users.toArray(new User[]{});
        }else{
            User[] u=new User[1];
            User u1=new User();
            u1.setUsername("无结果");
            return u;
        }
    }


    public User[] getAllUser() {
        Vector<User> users=new Vector<>();
        Cursor cursor=null;
        String sql = "select * from " + TABLENAME;
        System.out.println(sql);
        try {
            cursor = db.find("select * from " + TABLENAME,null);
            while(cursor.moveToNext()){
                User user=new User();
                user.setDB_id(cursor.getInt(cursor.getColumnIndex("DB_id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex(User.NAME)));
                user.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                user.setUnit(cursor.getString(cursor.getColumnIndex(User.UNIT)));
                user.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        System.out.println(users.size());
        if(users.size()>0){
            return users.toArray(new User[]{});
        }else{
            User[] us=new User[1];
            User u1=new User();
            u1.setUsername("无结果");
            us[0]=u1;
            return us;
        }
    }
}
