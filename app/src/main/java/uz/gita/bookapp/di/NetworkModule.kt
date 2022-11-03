package uz.gita.bookapp.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun getFireStore(): FirebaseFirestore = Firebase.firestore

    @[Provides Singleton]
    fun getFireDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @[Provides Singleton]
    fun getFireStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}