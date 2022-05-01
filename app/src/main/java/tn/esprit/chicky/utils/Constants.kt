package tn.esprit.chicky.utils

object Constants {
    const val PORT = "5000"

    // Emulateur
    //const val BASE_URL = "http://10.0.2.2:$PORT/"

    // Device
    const val BASE_URL = "http://192.168.1.41:$PORT/"

    const val BASE_URL_IMAGES = BASE_URL + "images-files/"
    const val BASE_URL_VIDEOS = BASE_URL + "videos-files/"
    const val BASE_URL_MUSIC = BASE_URL + "music-files/"

    const val SHARED_PREF_SESSION = "SESSION"
}