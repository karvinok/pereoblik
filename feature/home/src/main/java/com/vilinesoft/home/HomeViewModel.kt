package com.vilinesoft.home

import com.vilinesoft.home.HomeContract.UIEffect
import com.vilinesoft.home.HomeContract.UIIntent
import com.vilinesoft.home.HomeContract.UIState
import com.vilinesoft.ui.BaseViewModel

class HomeViewModel : BaseViewModel<UIIntent, UIState, UIEffect>() {

    override fun provideDefaultState() = UIState()

    override fun handleIntent(intent: UIIntent) {

    }
}