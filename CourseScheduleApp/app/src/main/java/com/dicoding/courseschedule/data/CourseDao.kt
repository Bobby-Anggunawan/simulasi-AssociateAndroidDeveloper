package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2(DONE) : Define data access object (DAO)
@Dao
interface CourseDao {

    @RawQuery(observedEntities = arrayOf(Course::class))
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>

    @RawQuery(observedEntities = arrayOf(Course::class))
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("Select * from Course where Course.id = :id")
    fun getCourse(id: Int): LiveData<Course>

    @Query("Select * from Course where Course.day = :day")
    fun getTodaySchedule(day: Int): List<Course>

    @Insert
    fun insert(course: Course)

    @Delete
    fun delete(course: Course)
}