package com.julhdev.notes.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Objeto que representa los modulos empleados en Firebase
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

  /**
   * Proporciona una instancia de FirebaseAuth
   * @return Instancia de FirebaseAuth
   */
  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

  /**
   * Proporciona una instancia de FirebaseFirestore
   * @return Instancia de FirebaseFirestore
   */
  @Provides
  @Singleton
  fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}