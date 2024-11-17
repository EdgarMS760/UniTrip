package com.psm.unitrip.Data

class SetDB {

    companion object{
        val DB_NAME =  "db_UniTrip"
        val DB_VERSION =  1
    }

    abstract class tableUsers{
        companion object{
            val TABLE_NAME = "Usuarios"
            val COL_IDUSUARIO =  "idUsuario"
            val COL_EMAIL =  "email"
            val COL_PASSWORD= "password"
            val COL_NOMBRE =  "nombre"
            val COL_APELLIDO =  "apellido"
            val COL_USERNAME =  "username"
            val COL_DIRECCION =  "direccion"
            val COL_TELEFONO =  "telefono"
            val COL_STATUS =  "status"
            val COL_PROFILEPIC =  "profilePic"
            val COL_FECHACREACION =  "fechaCreacion"
            val COL_FECHAMODIFICACION =  "fechaModificacion"
        }
    }

    abstract class tableChats{
        companion object{
            val TABLE_NAME = "Chats"
            val COL_IDCHAT =  "idChat"
            val COL_IDEMISOR =  "idEmisor"
            val COL_IDRECEPTOR =  "idReceptor"
            val COL_FECHACREACION=  "fechaCreacion"
        }
    }

    abstract class tableMensajes{
        companion object{
            val TABLE_NAME = "Mensajes"
            val COL_ID =  "idMensaje"
            val COL_MENSAJE =  "mensaje"
            val COL_IDEMISOR =  "idEmisor"
            val COL_IDCHATPERTENECIENTE=  "idChatPerteneciente"
            val COL_FECHACREACION=  "fechaCreacion"
        }
    }

    abstract class tablePost{
        companion object{
            val TABLE_NAME = "Posts"
            val COL_IDPOST =  "idPost"
            val COL_FECHACREACION =  "fechaCreacion"
            val COL_FECHAMODIFICACION =  "fechaModificacion"
            val COL_IDUSUARIO =  "idUsuario"
            val COL_TITLE =  "title"
            val COL_DESCRIPTION =  "description"
            val COL_PRECIO =  "precio"
            val COL_STATUS =  "status"
            val COL_LOCATION =  "location"
        }
    }


    abstract class tableDraftPost{
        companion object{
            val TABLE_NAME = "Drafts"
        }
    }


    abstract class tableImagenes{
        companion object{
            val TABLE_NAME = "Imagenes"
            val COL_IDIMAGEN = "idImagen"
            val COL_IDPOST =  "idPost"
            val COL_IMAGEN =  "imagen"
        }
    }


    abstract class tableDraftImagenes{
        companion object{
            val TABLE_NAME = "DraftImages"
        }
    }

}