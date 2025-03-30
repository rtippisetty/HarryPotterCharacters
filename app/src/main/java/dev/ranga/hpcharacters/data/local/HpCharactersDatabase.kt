package dev.ranga.hpcharacters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HpCharacterEntity::class], version = 1, exportSchema = false)
abstract class HpCharactersDatabase : RoomDatabase() {
    abstract val hpCharacterDao: HpCharacterDao
}