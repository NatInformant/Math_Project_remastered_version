package com.example.healthypetsadvisor.math_project_remastered_version.di

import android.app.Application
import android.content.Context
import com.example.healthypetsadvisor.math_project_remastered_version.di.modules.ViewModelModule
import com.example.healthypetsadvisor.math_project_remastered_version.di.viewModel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@AppComponentScope
@Component(
    modules = [ViewModelModule::class]
)
interface AppComponent {
    @AppComponentScope
    fun getViewModelFactory(): ViewModelFactory
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): AppComponent
    }
}
