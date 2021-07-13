package com.dicoding.courseschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

//TODO 3(DONE___) : Define room database class
@Database(entities = arrayOf(Course::class), version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao

    companion object {

        @Volatile
        private var INSTANCE: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java,
                    "courses.db"
                ).build()
                INSTANCE = instance

                //pre load database
                val sharedPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
                val isLoaded = sharedPref.getBoolean("isLoaded", false)
                if(!isLoaded){
                    sharedPref.edit().putBoolean("isLoaded", true).apply()
                    runBlocking{
                        async(Dispatchers.IO){fillWithStartingData(instance.courseDao())}.await()
                    }
                }

                instance
            }
        }

        private fun fillWithStartingData(dao: CourseDao){
            dao.insert(Course(1, "Matematika", 1, "23:00","23:50", "Guru Matematika", "Belajar mtk"))
            dao.insert(Course(2, "Bahasa Indonesia", 2, "23:00","23:50", "Guru B.Indo", "Belajar b indo"))
            dao.insert(Course(3, "Algoritma", 3, "23:00","23:50", "Guru Algoritma", "Belajar algo"))
            dao.insert(Course(4, "PPKN", 4, "23:00","23:50", "Guru PPKN", "Belajar pkn"))
            dao.insert(Course(5, "Biologi", 5, "23:00","23:50", "Guru Biologi", "Belajar bio"))
            dao.insert(Course(6, "Fisika", 6, "23:00","23:50", "Guru Fisika", "Belajar fisika"))
            dao.insert(Course(7, "Ekonomi", 7, "23:00","23:50", "Guru Ekonomi", "Belajar ekonomi"))
        }

    }
}
