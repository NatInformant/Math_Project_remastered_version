package com.example.healthypetsadvisor.math_project_remastered_version

import android.app.Application
import com.example.healthypetsadvisor.math_project_remastered_version.di.AppComponent
import com.example.healthypetsadvisor.math_project_remastered_version.di.DaggerAppComponent
import com.example.healthypetsadvisor.math_project_remastered_version.di.viewModel.ViewModelFactory

class App : Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .application(this)
            .build()
        viewModelFactory = appComponent.getViewModelFactory()
    }

    companion object {
        lateinit var viewModelFactory: ViewModelFactory
    }
}
