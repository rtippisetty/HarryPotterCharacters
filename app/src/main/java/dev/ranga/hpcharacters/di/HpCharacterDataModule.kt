package dev.ranga.hpcharacters.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ranga.hpcharacters.BuildConfig
import dev.ranga.hpcharacters.data.HpCharactersRepositoryImpl
import dev.ranga.hpcharacters.data.local.HpCharacterDao
import dev.ranga.hpcharacters.data.local.HpCharactersDatabase
import dev.ranga.hpcharacters.data.mapper.CharacterModelMapper
import dev.ranga.hpcharacters.data.remote.HpCharacterService
import dev.ranga.hpcharacters.domain.HpCharactersRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HpCharacterDataModule {
    private val defaultJson = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideCharactersRepository(
        hpCharacterService: HpCharacterService,
        hpCharacterDao: HpCharacterDao,
        hpCharacterMapper: CharacterModelMapper,
    ): HpCharactersRepository {
        return HpCharactersRepositoryImpl(
            hpCharacterService,
            hpCharacterDao,
            hpCharacterMapper
        )
    }

    @Provides
    @Singleton
    fun provideHpCharacterService(client: OkHttpClient): HpCharacterService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(HpCharacterService.BASE_URL)
            .client(client)
            .addConverterFactory(defaultJson.asConverterFactory(contentType))
            .build()
            .create(HpCharacterService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            if (BuildConfig.DEBUG) {
                Log.d("HpCharacterService", "Request: ${request.url}")
            }
            chain.proceed(request)
        }
    }

    @Provides
    fun provideRecipeDao(database: HpCharactersDatabase): HpCharacterDao {
        return database.hpCharacterDao
    }

    @Singleton
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context): HpCharactersDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = HpCharactersDatabase::class.java,
                name = "characters_database"
            )
            .fallbackToDestructiveMigration(true)
            .build()
    }

}