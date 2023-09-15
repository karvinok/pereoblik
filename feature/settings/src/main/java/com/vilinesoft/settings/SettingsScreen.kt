package com.vilinesoft.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,

    viewModel: SettingsViewModel = koinViewModel()
){
   Column (
       modifier = modifier
           .fillMaxSize()
           .background(Color.Yellow)
           .padding(8.dp)
   ){
       Text(text = "IP адреса сервера",  color= Color.Black )
       //TextField(value = "dwdwdwd", onValueChange = {onCommentChanged(it.text)})
       Text(text = "Префікс вагового товару",  color= Color.Black )
       //TextField(value = "dwdwdwd", onValueChange = {onCommentChanged(it.text)})


   }

}