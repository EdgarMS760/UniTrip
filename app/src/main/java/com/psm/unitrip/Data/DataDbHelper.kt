package com.psm.unitrip.Data


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class DataDbHelper (var context: Context): SQLiteOpenHelper(context,SetDB.DB_NAME,null,SetDB.DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?){
        try{
            Toast.makeText(context, "Se creo la base?", Toast.LENGTH_SHORT).show()
            val createtableUsers:String =  "CREATE TABLE " + SetDB.tableUsers.TABLE_NAME + "(" +
                    SetDB.tableUsers.COL_IDUSUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tableUsers.COL_EMAIL + " VARCHAR(100)," +
                    SetDB.tableUsers.COL_PASSWORD + " VARCHAR(20)," +
                    SetDB.tableUsers.COL_NOMBRE + " VARCHAR(50)," +
                    SetDB.tableUsers.COL_APELLIDO + " VARCHAR(50)," +
                    SetDB.tableUsers.COL_USERNAME + " VARCHAR(50)," +
                    SetDB.tableUsers.COL_DIRECCION + " VARCHAR(100)," +
                    SetDB.tableUsers.COL_TELEFONO + " VARCHAR(10)," +
                    SetDB.tableUsers.COL_STATUS + " CHAR(1)," +
                    SetDB.tableUsers.COL_PROFILEPIC + " TEXT," +
                    SetDB.tableUsers.COL_FECHACREACION + " TEXT," +
                    SetDB.tableUsers.COL_FECHAMODIFICACION + " TEXT)"

            db?.execSQL(createtableUsers)

            val createtableChat:String =  "CREATE TABLE " + SetDB.tableChats.TABLE_NAME + "(" +
                    SetDB.tableChats.COL_IDCHAT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tableChats.COL_IDEMISOR + " INTEGER," +
                    SetDB.tableChats.COL_IDRECEPTOR + " INTEGER," +
                    SetDB.tableChats.COL_FECHACREACION + " TEXT," +
                    "FOREIGN KEY(" + SetDB.tableChats.COL_IDEMISOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + ")," +
                    "FOREIGN KEY(" + SetDB.tableChats.COL_IDRECEPTOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + "))"


            db?.execSQL(createtableChat)

            val createtableMensajes:String =  "CREATE TABLE " + SetDB.tableMensajes.TABLE_NAME + "(" +
                    SetDB.tableMensajes.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tableMensajes.COL_MENSAJE + " TEXT," +
                    SetDB.tableMensajes.COL_IDEMISOR + " INTEGER," +
                    SetDB.tableMensajes.COL_IDCHATPERTENECIENTE + " INTEGER," +
                    SetDB.tableMensajes.COL_FECHACREACION + " TEXT," +
                    "FOREIGN KEY(" + SetDB.tableMensajes.COL_IDEMISOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + ")," +
                    "FOREIGN KEY(" + SetDB.tableMensajes.COL_IDCHATPERTENECIENTE + ") REFERENCES " + SetDB.tableChats.TABLE_NAME + "(" + SetDB.tableChats.COL_IDCHAT + "))"


            db?.execSQL(createtableMensajes)

            val createtablePost:String =  "CREATE TABLE " + SetDB.tablePost.TABLE_NAME + "(" +
                    SetDB.tablePost.COL_IDPOST + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tablePost.COL_IDUSUARIO + " INTEGER," +
                    SetDB.tablePost.COL_TITLE + " VARCHAR(100)," +
                    SetDB.tablePost.COL_DESCRIPTION + " TEXT," +
                    SetDB.tablePost.COL_PRECIO + " TEXT," +
                    SetDB.tablePost.COL_STATUS + " CHAR(1)," +
                    SetDB.tablePost.COL_LOCATION + " VARCHAR(150)," +
                    SetDB.tablePost.COL_FECHAMODIFICACION + " TEXT," +
                    SetDB.tablePost.COL_FECHACREACION + " TEXT," +
                    "FOREIGN KEY(" + SetDB.tablePost.COL_IDUSUARIO + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + "))"


            db?.execSQL(createtablePost)


            val createtableImagenes:String =  "CREATE TABLE " + SetDB.tableImagenes.TABLE_NAME + "(" +
                    SetDB.tableImagenes.COL_IDIMAGEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tableImagenes.COL_IDPOST + " INTEGER," +
                    SetDB.tableImagenes.COL_IMAGEN + " TEXT," +
                    "FOREIGN KEY(" + SetDB.tableImagenes.COL_IDPOST + ") REFERENCES " + SetDB.tablePost.TABLE_NAME + "(" + SetDB.tablePost.COL_IDPOST + "))"


            db?.execSQL(createtableImagenes)

            Log.e("ENTRO","CREO TABLAS")

        }catch (e: Exception){
            Log.e("Execption", e.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}