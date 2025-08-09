package ru.ikom.manual_di.app

import android.app.Application
import ru.ikom.manual_di.di.DefaultAppContainer

class App : Application() {

    val appContainer by lazy { DefaultAppContainer(this) }
}