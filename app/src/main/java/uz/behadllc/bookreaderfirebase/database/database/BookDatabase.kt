package uz.behadllc.bookreaderfirebase.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.behadllc.bookreaderfirebase.database.entitiy.BookModelDatabase
import uz.behadllc.bookreaderfirebase.database.dao.BookDao

@Database(entities = [BookModelDatabase::class],
    exportSchema = false,
    version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book-database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}