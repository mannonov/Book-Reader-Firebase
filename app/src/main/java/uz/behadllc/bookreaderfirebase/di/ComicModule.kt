package uz.behadllc.bookreaderfirebase.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import uz.behadllc.bookreaderfirebase.utils.Constants.BOOK_COLLECTION
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ComicModule {
    @Singleton
    @Provides
    fun provideFireStore(): CollectionReference =
        Firebase.firestore.collection(BOOK_COLLECTION)
}