package com.afterloop.wavem.routes

sealed class WavemRoutes(val route: String) {
    object Library : WavemRoutes("library")
    object Converter : WavemRoutes("converter")
}