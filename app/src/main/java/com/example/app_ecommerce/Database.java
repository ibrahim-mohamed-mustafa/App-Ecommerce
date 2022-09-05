package com.example.app_ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String db_name ="mydb";
    public Database(@Nullable Context context){
        super(context, db_name, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String Category="create table  Category(CatID integer primary KEY  ,Name text);";
        db.execSQL(Category);
        String Item = "CREATE TABLE Item(ItemID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT NOT NULL ,Description  TEXT NOT NULL,Price INTEGER,Brand  TEXT NOT NULL,url INTEGER,Cat_ID INTEGER,FOREIGN KEY (Cat_ID) REFERENCES Category (CatID));";
        db.execSQL(Item);
        String User="create table  User(userid INTEGER primary KEY AUTOINCREMENT ,Name text not null,Password text not null,birthdate text not null);";
        db.execSQL(User);
        String Cart="create table  Cart(cartid INTEGER primary KEY AUTOINCREMENT ,Price integer ,user_id INTEGER,address String not null,FOREIGN KEY (user_id) REFERENCES User (userid));";
        db.execSQL(Cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists Item");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Cart");
        onCreate(db);
    }
    public boolean add_User(String Name,String pass,String birthdate){
          Cursor cursor;
          cursor=getAllUser();
          do {
              if(cursor.getCount()<=0||cursor==null)break;
              if(cursor.getString(cursor.getColumnIndex("Name")).equals(Name))return  false;
          }while (cursor.moveToNext());
            ContentValues con = new ContentValues();
            con.put("Name", Name);
            con.put("Password", pass);
            con.put("birthdate", birthdate);
            db = getWritableDatabase();
            db.insert("User", null, con);
            db.close();
            return  true;
    }
    public void add_Cart(Integer price,Integer user_id,String location){
        ContentValues con = new ContentValues();
        con.put("Price", price);
        con.put("user_id", user_id);
        con.put("address", location);
        db = getWritableDatabase();
        db.insert("Cart", null, con);
        db.close();
    }
    public void add_Item(String Name,String Description ,String Brand,Integer price,Integer catid,Integer url){
        ContentValues con = new ContentValues();
        con.put("Name", Name);
        con.put("Description", Description);
        con.put("Price", price);
        con.put("Brand", Brand);
        con.put("url", url);
        con.put("Cat_ID", catid);
        db = getWritableDatabase();
        db.insert("Item", null, con);
        db.close();
    }
    public  void add_category(String Name,Integer id){
        ContentValues con=new ContentValues();
        con.put("CatID",id);
        con.put("Name",Name);
        db=getWritableDatabase();
        db.insert("Category",null,con);
        db.close();
    }
    public  Cursor getAllItems(){
        db=getReadableDatabase();
        Cursor cursor=db.query("Item",null,null,null,null,null,null,null);
        if(cursor.getCount()>0&&cursor!=null)
            cursor.moveToFirst();
        db.close();
        return  cursor;
    }
    public  Cursor getAllUser(){
        db=getReadableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null,null);
        if(cursor.getCount()>0&&cursor!=null)
            cursor.moveToFirst();
        db.close();
        return  cursor;
    }
    public void delete_User(String Name){
        db=getWritableDatabase();
        db.delete("User","Name=?" ,new String[]{Name});
    }
}
