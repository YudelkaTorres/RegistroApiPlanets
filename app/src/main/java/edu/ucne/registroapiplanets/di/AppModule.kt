package edu.ucne.registroapiplanets.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroapiplanets.data.remote.DragonBallApi
import edu.ucne.registroapiplanets.data.remote.remotedatasource.PlanetRemoteDataSource
import edu.ucne.registroapiplanets.data.repository.PlanetRepositoryImpl
import edu.ucne.registroapiplanets.domain.repository.PlanetRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): DragonBallApi {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DragonBallApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: PlanetRemoteDataSource): PlanetRepository {
        return PlanetRepositoryImpl(remoteDataSource)
    }
}