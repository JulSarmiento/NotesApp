package com.julhdev.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * NotesApplication es la clase Application para la aplicación de notas.
 * Está anotada con @HiltAndroidApp para habilitar la inyección de dependencias con Dagger Hilt.
 * @usage Define esta clase en el AndroidManifest.xml para inicializar Hilt al iniciar la aplicación.
 */
@HiltAndroidApp
class NotesApplication: Application() {
}

