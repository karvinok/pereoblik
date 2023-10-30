package com.vilinesoft.navigation.di

import com.vilinesoft.document_edit.DocumentEditViewModel
import com.vilinesoft.documents.DocumentsViewModel
import com.vilinesoft.handbook.HandbookViewModel
import com.vilinesoft.home.HomeViewModel
import com.vilinesoft.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object VMModule {
    fun get() = module {
        viewModel { HomeViewModel() }
        viewModel { HandbookViewModel(get(), get()) }
        viewModel { DocumentsViewModel(get()) }
        viewModel { DocumentEditViewModel(get(), get(), get(), get()) }
        viewModel { SettingsViewModel(get())}
    }
}