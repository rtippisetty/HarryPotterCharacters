package dev.ranga.hpcharacters.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HpCharacterDao {
    @Query("SELECT * FROM hp_characters")
    fun getAllCharacters(): Flow<List<HpCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<HpCharacterEntity>)

    @Query("SELECT * FROM hp_characters WHERE id = :id")
    suspend fun getCharacterById(id: String): HpCharacterEntity?
}