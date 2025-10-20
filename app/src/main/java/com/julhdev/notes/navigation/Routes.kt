package com.julhdev.notes.navigation

/**
 * Define las rutas de navegación de la aplicación.
 * - HOME: Ruta para la vista principal (HomeView).
 * - ADD: Ruta para la vista de agregar una nueva nota (AddView).
 * - EDIT: Ruta para la vista de editar una nota existente, con un parámetro dinámico 'id'.
 * @usage Utilizar estas constantes para navegar entre las diferentes vistas de la aplicación.
 */
object Routes {
  const val SPLASH = "splash"
  const val ONBOARDING =  "onboarding"
  const val HOME = "home"
  const val ADD = "add"
  const val EDIT = "edit/{id}"
}