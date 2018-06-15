package com.prueba.besil.theelectricfactoryprueba.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import javax.inject.Inject

class MyDatabaseOpenHelper @Inject constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "thefDB", null, 7) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable("pedidos", true,
                "id" to INTEGER + PRIMARY_KEY  + AUTOINCREMENT,
                "id_client" to INTEGER + NOT_NULL,
                "date" to TEXT)
        db.createTable("pedidos_productos", true,
                "id_pedido" to SqlType.create("INTEGER NOT_NULL PRIMARY_KEY"),
                "id_product" to SqlType.create("INTEGER NOT_NULL PRIMARY_KEY"),
                "quantity" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_pedido", "pedidos", "id"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("id_pedido", true)
        db.dropTable("pedidos", true)
    }
}
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)