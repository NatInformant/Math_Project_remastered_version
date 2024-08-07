package com.example.healthypetsadvisor.math_project_remastered_version.di.viewModel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
internal annotation class ViewModelKey(val value:KClass<out ViewModel>)
