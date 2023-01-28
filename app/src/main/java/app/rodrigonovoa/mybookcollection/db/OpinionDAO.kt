package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.OpinionEntity

@Dao
interface OpinionDAO {
    @Insert
    fun insertOpinion(opinion: OpinionEntity): Long

    @Query("select * from opinion")
    fun getAllOpinion(): List<OpinionEntity>

    @Update
    fun updateOpinion(opinion: OpinionEntity)

    @Delete
    fun deleteOpinion(opinion: OpinionEntity)
}