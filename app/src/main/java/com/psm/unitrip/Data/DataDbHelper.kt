package com.psm.unitrip.Data


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.Exception

class DataDbHelper(var context: Context) :
    SQLiteOpenHelper(context, SetDB.DB_NAME, null, SetDB.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createtableUsers: String = "CREATE TABLE " + SetDB.tableUsers.TABLE_NAME + "(" +
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

            val createtableChat: String = "CREATE TABLE " + SetDB.tableChats.TABLE_NAME + "(" +
                    SetDB.tableChats.COL_IDCHAT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tableChats.COL_IDEMISOR + " INTEGER," +
                    SetDB.tableChats.COL_IDRECEPTOR + " INTEGER," +
                    SetDB.tableChats.COL_FECHACREACION + " TEXT," +
                    "FOREIGN KEY(" + SetDB.tableChats.COL_IDEMISOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + ")," +
                    "FOREIGN KEY(" + SetDB.tableChats.COL_IDRECEPTOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + "))"


            db?.execSQL(createtableChat)

            val createtableMensajes: String =
                "CREATE TABLE " + SetDB.tableMensajes.TABLE_NAME + "(" +
                        SetDB.tableMensajes.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SetDB.tableMensajes.COL_MENSAJE + " TEXT," +
                        SetDB.tableMensajes.COL_IDEMISOR + " INTEGER," +
                        SetDB.tableMensajes.COL_IDCHATPERTENECIENTE + " INTEGER," +
                        SetDB.tableMensajes.COL_FECHACREACION + " TEXT," +
                        "FOREIGN KEY(" + SetDB.tableMensajes.COL_IDEMISOR + ") REFERENCES " + SetDB.tableUsers.TABLE_NAME + "(" + SetDB.tableUsers.COL_IDUSUARIO + ")," +
                        "FOREIGN KEY(" + SetDB.tableMensajes.COL_IDCHATPERTENECIENTE + ") REFERENCES " + SetDB.tableChats.TABLE_NAME + "(" + SetDB.tableChats.COL_IDCHAT + "))"


            db?.execSQL(createtableMensajes)

            val createtablePost: String = "CREATE TABLE " + SetDB.tablePost.TABLE_NAME + "(" +
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


            val createtableImagenes: String =
                "CREATE TABLE " + SetDB.tableImagenes.TABLE_NAME + "(" +
                        SetDB.tableImagenes.COL_IDIMAGEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SetDB.tableImagenes.COL_IDPOST + " INTEGER," +
                        SetDB.tableImagenes.COL_IMAGEN + " TEXT," +
                        "FOREIGN KEY(" + SetDB.tableImagenes.COL_IDPOST + ") REFERENCES " + SetDB.tablePost.TABLE_NAME + "(" + SetDB.tablePost.COL_IDPOST + "))"


            db?.execSQL(createtableImagenes)


            val createtableDraft: String = "CREATE TABLE " + SetDB.tableDraftPost.TABLE_NAME + "(" +
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


            db?.execSQL(createtableDraft)


            val createtableDraftImagenes: String =
                "CREATE TABLE " + SetDB.tableDraftImagenes.TABLE_NAME + "(" +
                        SetDB.tableImagenes.COL_IDIMAGEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SetDB.tableImagenes.COL_IDPOST + " INTEGER," +
                        SetDB.tableImagenes.COL_IMAGEN + " TEXT," +
                        "FOREIGN KEY(" + SetDB.tableImagenes.COL_IDPOST + ") REFERENCES " + SetDB.tableDraftPost.TABLE_NAME + "(" + SetDB.tablePost.COL_IDPOST + "))"

            db?.execSQL(createtableDraftImagenes)


            Log.e("ENTRO", "CREO TABLAS")

        } catch (e: Exception) {
            Log.e("Execption", e.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //Insert functions
    public fun insertUsuario(user: Usuario): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        values.put(SetDB.tableUsers.COL_EMAIL, user.email)
        values.put(SetDB.tableUsers.COL_PASSWORD, user.password)
        values.put(SetDB.tableUsers.COL_NOMBRE, user.nombre)
        values.put(SetDB.tableUsers.COL_APELLIDO, user.apellido)
        values.put(SetDB.tableUsers.COL_USERNAME, user.username)
        values.put(SetDB.tableUsers.COL_DIRECCION, user.direccion)
        values.put(SetDB.tableUsers.COL_TELEFONO, user.telefono)
        values.put(SetDB.tableUsers.COL_PROFILEPIC, user.profilePic)
        values.put(SetDB.tableUsers.COL_FECHACREACION, "")
        values.put(SetDB.tableUsers.COL_FECHAMODIFICACION, "")
        values.put(SetDB.tableUsers.COL_PROFILEPIC, user.profilePic)
        values.put(SetDB.tableUsers.COL_STATUS, "A")

        try {
            val result = database.insert(SetDB.tableUsers.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertChats(chatInstance: Chat): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true



        values.put(SetDB.tableChats.COL_IDRECEPTOR, chatInstance.idReceptor)
        values.put(SetDB.tableChats.COL_IDEMISOR, chatInstance.idEmisor)
        values.put(SetDB.tableChats.COL_FECHACREACION, "")

        try {
            val result = database.insert(SetDB.tableChats.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertMensaje(mensajeInstance: Mensaje): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        values.put(SetDB.tableMensajes.COL_MENSAJE, mensajeInstance.mensaje)
        values.put(SetDB.tableMensajes.COL_IDEMISOR, mensajeInstance.idEmisor)
        values.put(SetDB.tableMensajes.COL_IDCHATPERTENECIENTE, mensajeInstance.idChatPerteneciente)
        values.put(SetDB.tableMensajes.COL_FECHACREACION, currentDate)

        try {
            val result = database.insert(SetDB.tableMensajes.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertPost(postInstance: Post): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        values.put(SetDB.tablePost.COL_IDUSUARIO, postInstance.idUsuario)
        values.put(SetDB.tablePost.COL_TITLE, postInstance.title)
        values.put(SetDB.tablePost.COL_DESCRIPTION, postInstance.description)
        values.put(SetDB.tablePost.COL_PRECIO, postInstance.precio)
        values.put(SetDB.tablePost.COL_STATUS, postInstance.status)
        values.put(SetDB.tablePost.COL_LOCATION, postInstance.location)
        values.put(SetDB.tablePost.COL_FECHACREACION, currentDate)

        try {
            val result = database.insert(SetDB.tablePost.TABLE_NAME, null, values)

            if (result == (0).toLong()) {

            } else {
                postInstance.arrayImagenes.forEach { imagen ->
                    this.insertImagen(result, imagen)
                }
            }
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertImagen(idPost: Long, imagen: String): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true



        values.put(SetDB.tableImagenes.COL_IDPOST, idPost)
        values.put(SetDB.tableImagenes.COL_IMAGEN, imagen)

        try {
            val result = database.insert(SetDB.tableImagenes.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertImagenDraft(idPost: Long, imagen: String): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true



        values.put(SetDB.tableImagenes.COL_IDPOST, idPost)
        values.put(SetDB.tableImagenes.COL_IMAGEN, imagen)

        try {
            val result = database.insert(SetDB.tableDraftImagenes.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun insertDraft(draftInstance: Post): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        values.put(SetDB.tablePost.COL_IDUSUARIO, draftInstance.idUsuario)
        values.put(SetDB.tablePost.COL_TITLE, draftInstance.title)
        values.put(SetDB.tablePost.COL_DESCRIPTION, draftInstance.description)
        values.put(SetDB.tablePost.COL_PRECIO, draftInstance.precio)
        values.put(SetDB.tablePost.COL_STATUS, "D")
        values.put(SetDB.tablePost.COL_LOCATION, draftInstance.location)
        values.put(SetDB.tablePost.COL_FECHACREACION, currentDate)

        try {
            val result = database.insert(SetDB.tableDraftPost.TABLE_NAME, null, values)

            if (result == (0).toLong()) {

            } else {
                draftInstance.arrayImagenes.forEach { imagen ->
                    this.insertImagenDraft(result, imagen)
                }
            }
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }


    //Update Functions
    public fun updateDraft(draftInstance: Post): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true



        values.put(SetDB.tablePost.COL_TITLE, draftInstance.title)
        values.put(SetDB.tablePost.COL_DESCRIPTION, draftInstance.description)
        values.put(SetDB.tablePost.COL_PRECIO, draftInstance.precio)
        values.put(SetDB.tablePost.COL_LOCATION, draftInstance.location)

        val where: String = SetDB.tablePost.COL_IDPOST + "=?"

        try {
            val result = database.update(
                SetDB.tableDraftPost.TABLE_NAME,
                values,
                where,
                arrayOf(draftInstance.idPost.toString())
            )

            if (result != -1) {

                if (draftInstance.arrayImagenes.isNotEmpty()) {
                    this.deleteImagesDraft(draftInstance.idPost, database)
                    draftInstance.arrayImagenes.forEach { imagen ->
                        this.insertImagenDraft(draftInstance.idPost.toLong(), imagen)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun updatePost(postInstance: Post): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true



        values.put(SetDB.tablePost.COL_TITLE, postInstance.title)
        values.put(SetDB.tablePost.COL_DESCRIPTION, postInstance.description)
        values.put(SetDB.tablePost.COL_PRECIO, postInstance.precio)
        values.put(SetDB.tablePost.COL_LOCATION, postInstance.location)

        val where: String = SetDB.tablePost.COL_IDPOST + "=?"

        try {
            val result = database.update(
                SetDB.tablePost.TABLE_NAME,
                values,
                where,
                arrayOf(postInstance.idPost.toString())
            )

            if (result != -1) {

                if (postInstance.arrayImagenes.isNotEmpty()) {
                    this.deleteImages(postInstance.idPost, database)
                    postInstance.arrayImagenes.forEach { imagen ->
                        this.insertImagen(postInstance.idPost.toLong(), imagen)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun updateUsuario(userInstance: Usuario): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        values.put(SetDB.tableUsers.COL_PASSWORD, userInstance.password)
        values.put(SetDB.tableUsers.COL_TELEFONO, userInstance.telefono)
        values.put(SetDB.tableUsers.COL_USERNAME, userInstance.username)
        if (userInstance.profilePic.isNotEmpty()) {
            values.put(SetDB.tableUsers.COL_PROFILEPIC, userInstance.profilePic)
        }


        val where: String = SetDB.tableUsers.COL_IDUSUARIO + "=?"
        try {

            val result = database.update(
                SetDB.tableUsers.TABLE_NAME,
                values,
                where,
                arrayOf(userInstance.idUsuario.toString())
            )
        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }


    //Delete Functions
    public fun deletePost(intID: Int): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        values.put(SetDB.tablePost.COL_STATUS, "E")

        val where: String = SetDB.tablePost.COL_IDPOST + "=?"

        try {
            val result = database.update(
                SetDB.tablePost.TABLE_NAME,
                values,
                where,
                arrayOf(intID.toString())
            )

        } catch (e: Exception) {
            Log.e("Execption", e.toString())
            boolResult = false
        }
        database.close()

        return boolResult
    }

    public fun deleteDraft(intID: Int): Boolean {
        val db = this.writableDatabase
        var boolResult: Boolean = false
        try {
            if(this.deleteImagesDraft(intID, db)){
                val where: String = SetDB.tablePost.COL_IDPOST + "=?"
                val _success =
                    db.delete(SetDB.tableDraftPost.TABLE_NAME, where, arrayOf(intID.toString()))
                boolResult = Integer.parseInt("$_success") != -1
            }





        } catch (e: java.lang.Exception) {

            Log.e("Execption", e.toString())
        } finally {
            db.close()
        }

        return boolResult
    }

    public fun deleteImages(intID: Int, db: SQLiteDatabase): Boolean {
        var boolResult: Boolean = false
        try {

            val where: String = SetDB.tableImagenes.COL_IDPOST + "=?"
            val _success =
                db.delete(SetDB.tableImagenes.TABLE_NAME, where, arrayOf(intID.toString()))

            boolResult = Integer.parseInt("$_success") != -1


        } catch (e: java.lang.Exception) {

            Log.e("Execption", e.toString())
        }

        return boolResult
    }

    public fun deleteImagesDraft(intID: Int, db: SQLiteDatabase): Boolean {
        var boolResult: Boolean = false
        try {

            val where: String = SetDB.tableImagenes.COL_IDPOST + "=?"
            val _success =
                db.delete(SetDB.tableDraftImagenes.TABLE_NAME, where, arrayOf(intID.toString()))


            boolResult = Integer.parseInt("$_success") != -1


        } catch (e: java.lang.Exception) {

            Log.e("Execption", e.toString())
        }

        return boolResult
    }

    //Select Functions
    fun selectMisPosts(userId: Int): MutableList<Post> {
        val postList: MutableList<Post> = mutableListOf()
        val db: SQLiteDatabase = this.readableDatabase

        val postCursor = db.rawQuery(
            "SELECT P.${SetDB.tablePost.COL_IDPOST}, " +
                    "P.${SetDB.tablePost.COL_TITLE}, " +
                    "P.${SetDB.tablePost.COL_DESCRIPTION}, " +
                    "P.${SetDB.tablePost.COL_PRECIO}, " +
                    "P.${SetDB.tablePost.COL_STATUS}, " +
                    "P.${SetDB.tablePost.COL_LOCATION}, " +
                    "P.${SetDB.tablePost.COL_IDUSUARIO}, " +
                    "P.${SetDB.tablePost.COL_FECHACREACION}, " +
                    "U.username, " +
                    "U.profilePic " +
                    "FROM ${SetDB.tableDraftPost.TABLE_NAME} P " +
                    "INNER JOIN Usuarios U ON P.${SetDB.tablePost.COL_IDUSUARIO} = U.idUsuario " +
                    "WHERE P.${SetDB.tablePost.COL_IDUSUARIO} = ? " +
                    "ORDER BY P.${SetDB.tablePost.COL_FECHACREACION} DESC",
            arrayOf(userId.toString())
        )

        if (postCursor.moveToFirst()) {
            do {

                val idPost = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDPOST))
                val title = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_TITLE))
                val description = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_DESCRIPTION))
                val precio = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_PRECIO))
                val status = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_STATUS))
                val location = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_LOCATION))
                val idUsuario = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDUSUARIO))
                val fechaCreacion = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_FECHACREACION))
                val username = postCursor.getString(postCursor.getColumnIndexOrThrow("username"))
                val profilePic = postCursor.getString(postCursor.getColumnIndexOrThrow("profilePic"))

                val imageCursor = db.rawQuery(
                    "SELECT ${SetDB.tableImagenes.COL_IMAGEN} " +
                            "FROM ${SetDB.tableDraftImagenes.TABLE_NAME} " +
                            "WHERE ${SetDB.tableImagenes.COL_IDPOST} = ?",
                    arrayOf(idPost.toString())
                )

                val images = mutableListOf<String>()
                if (imageCursor.moveToFirst()) {
                    do {
                        images.add(imageCursor.getString(imageCursor.getColumnIndexOrThrow(SetDB.tableImagenes.COL_IMAGEN)))
                    } while (imageCursor.moveToNext())
                }
                imageCursor.close()

                val post = Post(
                    idPost = idPost,
                    title = title,
                    description = description,
                    precio = precio,
                    status = status,
                    location = location,
                    idUsuario = idUsuario,
                    username = username,
                    profilePic = profilePic,
                    arrayImagenes = images,
                    fechaCreacion = fechaCreacion
                )
                postList.add(post)

            } while (postCursor.moveToNext())
        }
        postCursor.close()
        return postList
    }

    fun selectMyPosts(userId: Int): MutableList<Post> {
        val postList: MutableList<Post> = mutableListOf()
        val db: SQLiteDatabase = this.readableDatabase

        val postCursor = db.rawQuery(
            "SELECT P.${SetDB.tablePost.COL_IDPOST}, " +
                    "P.${SetDB.tablePost.COL_TITLE}, " +
                    "P.${SetDB.tablePost.COL_DESCRIPTION}, " +
                    "P.${SetDB.tablePost.COL_PRECIO}, " +
                    "P.${SetDB.tablePost.COL_STATUS}, " +
                    "P.${SetDB.tablePost.COL_LOCATION}, " +
                    "P.${SetDB.tablePost.COL_IDUSUARIO}, " +
                    "P.${SetDB.tablePost.COL_FECHACREACION}, " +
                    "U.username, " +
                    "U.profilePic " +
                    "FROM ${SetDB.tablePost.TABLE_NAME} P " +
                    "INNER JOIN Usuarios U ON P.${SetDB.tablePost.COL_IDUSUARIO} = U.idUsuario " +
                    "WHERE P.${SetDB.tablePost.COL_IDUSUARIO} = ? " +
                    "AND P.${SetDB.tablePost.COL_STATUS} = 'A' " +
                    "ORDER BY P.${SetDB.tablePost.COL_FECHACREACION} DESC",
            arrayOf(userId.toString())
        )

        if (postCursor.moveToFirst()) {
            do {

                val idPost = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDPOST))
                val title = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_TITLE))
                val description = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_DESCRIPTION))
                val precio = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_PRECIO))
                val status = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_STATUS))
                val location = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_LOCATION))
                val idUsuario = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDUSUARIO))
                val fechaCreacion = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_FECHACREACION))
                val username = postCursor.getString(postCursor.getColumnIndexOrThrow("username"))
                val profilePic = postCursor.getString(postCursor.getColumnIndexOrThrow("profilePic"))

                val imageCursor = db.rawQuery(
                    "SELECT ${SetDB.tableImagenes.COL_IMAGEN} " +
                            "FROM ${SetDB.tableImagenes.TABLE_NAME} " +
                            "WHERE ${SetDB.tableImagenes.COL_IDPOST} = ?",
                    arrayOf(idPost.toString())
                )

                val images = mutableListOf<String>()
                if (imageCursor.moveToFirst()) {
                    do {
                        images.add(imageCursor.getString(imageCursor.getColumnIndexOrThrow(SetDB.tableImagenes.COL_IMAGEN)))
                    } while (imageCursor.moveToNext())
                }
                imageCursor.close()

                val post = Post(
                    idPost = idPost,
                    title = title,
                    description = description,
                    precio = precio,
                    status = status,
                    location = location,
                    idUsuario = idUsuario,
                    username = username,
                    profilePic = profilePic,
                    arrayImagenes = images,
                    fechaCreacion = fechaCreacion
                )
                postList.add(post)

            } while (postCursor.moveToNext())
        }
        postCursor.close()
        return postList
    }

    fun selectPosts(userId: Int): MutableList<Post> {
        val postList: MutableList<Post> = mutableListOf()
        val db: SQLiteDatabase = this.readableDatabase

        val postCursor = db.rawQuery(
            "SELECT P.${SetDB.tablePost.COL_IDPOST}, " +
                    "P.${SetDB.tablePost.COL_TITLE}, " +
                    "P.${SetDB.tablePost.COL_DESCRIPTION}, " +
                    "P.${SetDB.tablePost.COL_PRECIO}, " +
                    "P.${SetDB.tablePost.COL_STATUS}, " +
                    "P.${SetDB.tablePost.COL_LOCATION}, " +
                    "P.${SetDB.tablePost.COL_IDUSUARIO}, " +
                    "P.${SetDB.tablePost.COL_FECHACREACION}, " +
                    "U.username, " +
                    "U.profilePic " +
                    "FROM ${SetDB.tablePost.TABLE_NAME} P " +
                    "INNER JOIN Usuarios U ON P.${SetDB.tablePost.COL_IDUSUARIO} = U.idUsuario " +
                    "WHERE P.${SetDB.tablePost.COL_IDUSUARIO} != ? " +
                    "AND P.${SetDB.tablePost.COL_STATUS} = 'A' " +
                    "ORDER BY P.${SetDB.tablePost.COL_FECHACREACION} DESC",
            arrayOf(userId.toString())
        )

        if (postCursor.moveToFirst()) {
            do {

                val idPost = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDPOST))
                val title = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_TITLE))
                val description = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_DESCRIPTION))
                val precio = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_PRECIO))
                val status = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_STATUS))
                val location = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_LOCATION))
                val idUsuario = postCursor.getInt(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_IDUSUARIO))
                val fechaCreacion = postCursor.getString(postCursor.getColumnIndexOrThrow(SetDB.tablePost.COL_FECHACREACION))
                val username = postCursor.getString(postCursor.getColumnIndexOrThrow("username"))
                val profilePic = postCursor.getString(postCursor.getColumnIndexOrThrow("profilePic"))

                val imageCursor = db.rawQuery(
                    "SELECT ${SetDB.tableImagenes.COL_IMAGEN} " +
                            "FROM ${SetDB.tableImagenes.TABLE_NAME} " +
                            "WHERE ${SetDB.tableImagenes.COL_IDPOST} = ?",
                    arrayOf(idPost.toString())
                )

                val images = mutableListOf<String>()
                if (imageCursor.moveToFirst()) {
                    do {
                        images.add(imageCursor.getString(imageCursor.getColumnIndexOrThrow(SetDB.tableImagenes.COL_IMAGEN)))
                    } while (imageCursor.moveToNext())
                }
                imageCursor.close()

                val post = Post(
                    idPost = idPost,
                    title = title,
                    description = description,
                    precio = precio,
                    status = status,
                    location = location,
                    idUsuario = idUsuario,
                    username = username,
                    profilePic = profilePic,
                    arrayImagenes = images,
                    fechaCreacion = fechaCreacion
                )
                postList.add(post)

            } while (postCursor.moveToNext())
        }
        postCursor.close()
        return postList
    }

    fun selectChats(userId: Int): MutableList<Chat> {
        val chatList: MutableList<Chat> = mutableListOf()
        val db: SQLiteDatabase = this.readableDatabase

        val chatCursor = db.rawQuery(
            "SELECT C.${SetDB.tableChats.COL_IDCHAT}, " +
                    "C.${SetDB.tableChats.COL_IDEMISOR}, " +
                    "C.${SetDB.tableChats.COL_IDRECEPTOR}, " +
                    "(UR.${SetDB.tableUsers.COL_NOMBRE} || ' ' || UR.${SetDB.tableUsers.COL_APELLIDO}) AS nombreReceptor, " +
                    "(UE.${SetDB.tableUsers.COL_NOMBRE} || ' ' || UE.${SetDB.tableUsers.COL_APELLIDO}) AS nombreEmisor, " +
                    "UR.${SetDB.tableUsers.COL_PROFILEPIC} AS fotoReceptor, " +
                    "UE.${SetDB.tableUsers.COL_PROFILEPIC} AS fotoEmisor, " +
                    "UE.${SetDB.tableUsers.COL_EMAIL} AS emailEmisor, " +
                    "UR.${SetDB.tableUsers.COL_EMAIL} AS emailReceptor " +
                    "FROM ${SetDB.tableChats.TABLE_NAME} C " +
                    "INNER JOIN ${SetDB.tableUsers.TABLE_NAME} UE ON C.${SetDB.tableChats.COL_IDEMISOR} = UE.${SetDB.tableUsers.COL_IDUSUARIO} " +
                    "INNER JOIN ${SetDB.tableUsers.TABLE_NAME} UR ON C.${SetDB.tableChats.COL_IDRECEPTOR} = UR.${SetDB.tableUsers.COL_IDUSUARIO} " +
                    "WHERE C.${SetDB.tableChats.COL_IDEMISOR} = ? " +
                    "   OR C.${SetDB.tableChats.COL_IDRECEPTOR} = ?",
            arrayOf(userId.toString(), userId.toString())
        )

        if (chatCursor.moveToFirst()) {
            do {
                val idChat = chatCursor.getInt(chatCursor.getColumnIndexOrThrow(SetDB.tableChats.COL_IDCHAT))
                val idEmisor = chatCursor.getInt(chatCursor.getColumnIndexOrThrow(SetDB.tableChats.COL_IDEMISOR))
                val idReceptor = chatCursor.getInt(chatCursor.getColumnIndexOrThrow(SetDB.tableChats.COL_IDRECEPTOR))
                val nombreReceptor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("nombreReceptor"))
                val nombreEmisor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("nombreEmisor"))
                val fotoReceptor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("fotoReceptor"))
                val fotoEmisor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("fotoEmisor"))
                val emailEmisor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("emailEmisor"))
                val emailReceptor = chatCursor.getString(chatCursor.getColumnIndexOrThrow("emailReceptor"))

                val chat = Chat(
                    idChat = idChat,
                    idEmisor = idEmisor,
                    idReceptor = idReceptor,
                    nombreReceptor = nombreReceptor,
                    nombreEmisor = nombreEmisor,
                    fotoReceptor = fotoReceptor,
                    fotoEmisor = fotoEmisor,
                    emailEmisor = emailEmisor,
                    emailReceptor = emailReceptor
                )
                chatList.add(chat)
            } while (chatCursor.moveToNext())
        }
        chatCursor.close()
        return chatList
    }

    fun selectMessages(idChat: Int): MutableList<Mensaje>{
        val mensajeList: MutableList<Mensaje> = mutableListOf()
        val db: SQLiteDatabase = this.readableDatabase

        val columns:Array<String> =  arrayOf(SetDB.tableMensajes.COL_IDCHATPERTENECIENTE,
            SetDB.tableMensajes.COL_FECHACREACION,
            SetDB.tableMensajes.COL_MENSAJE,
            SetDB.tableMensajes.COL_IDEMISOR)

        val where:String =  SetDB.tableMensajes.COL_IDCHATPERTENECIENTE + "= ${idChat.toString()}"

        val data =  db.query(SetDB.tableMensajes.TABLE_NAME,
            columns,
            where,
            null,
            null,
            null,
            SetDB.tableMensajes.COL_ID + " ASC")

        if(data.moveToFirst()){
            do{
                val idChatPerteneciente = data.getInt(data.getColumnIndexOrThrow(SetDB.tableMensajes.COL_IDCHATPERTENECIENTE))
                val idEmisor = data.getInt(data.getColumnIndexOrThrow(SetDB.tableMensajes.COL_IDEMISOR))
                val msg = data.getString(data.getColumnIndexOrThrow(SetDB.tableMensajes.COL_MENSAJE))
                val fechaCreacion = data.getString(data.getColumnIndexOrThrow(SetDB.tableMensajes.COL_FECHACREACION))

                val mensaje:Mensaje = Mensaje(idEmisor, msg, idChatPerteneciente, fechaCreacion)


                mensajeList.add(mensaje)
            }while (data.moveToNext())
        }


        data.close()
        return mensajeList
    }

}