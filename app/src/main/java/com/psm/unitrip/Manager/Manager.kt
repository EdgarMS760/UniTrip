package com.psm.unitrip.Manager

interface Manager<T> {
    fun add(item: T, callback: (Boolean)-> Unit)

    fun update(item: T, callback: (Boolean)-> Unit){
        return
    }
    fun delete(id: Int, callback: (Boolean)-> Unit){
        return
    }
    fun getAll(id: Int, callback: (List<T>?) -> Unit){
        return
    }

    fun getOwn(id: Int,  callback: (T?) -> Unit): T?{
        return null
    }
}