package com.example.healthypetsadvisor.math_project_remastered_version.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthypetsadvisor.math_project_remastered_version.di.AppComponentScope
import com.example.healthypetsadvisor.math_project_remastered_version.di.viewModel.ViewModelFactory
import com.example.healthypetsadvisor.math_project_remastered_version.di.viewModel.ViewModelKey
import com.example.healthypetsadvisor.math_project_remastered_version.presenter.ticketsList.TicketsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @AppComponentScope
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TicketsListViewModel::class)
    abstract fun bindTicketsListViewModel(
        viewModel: TicketsListViewModel
    ): ViewModel
}
