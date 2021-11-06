package tech.gamedev.motorolasolutions.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.gamedev.motorolasolutions.R
import tech.gamedev.motorolasolutions.data.db.UserDao
import tech.gamedev.motorolasolutions.data.db.UserDatabase
import tech.gamedev.motorolasolutions.data.other.Constants.BASE_URL
import tech.gamedev.motorolasolutions.data.remote.api.UserApi
import tech.gamedev.motorolasolutions.repositories.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, java.util.concurrent.TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, java.util.concurrent.TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, java.util.concurrent.TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, java.util.concurrent.TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao ,userApi: UserApi, @ApplicationContext context: Context) = UserRepository(userDao, userApi, context)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : UserDatabase =
        UserDatabase.create(context)

    @Provides
    fun provideUserDao(database: UserDatabase) : UserDao {
        return database.getUserDao()
    }
}