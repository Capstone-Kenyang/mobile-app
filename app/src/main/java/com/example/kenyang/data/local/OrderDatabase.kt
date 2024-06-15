package com.example.kenyang.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.kenyang.converter.Converters
import com.example.kenyang.data.dataclass.Order
import androidx.room.RoomDatabase

@Database(entities = [Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}