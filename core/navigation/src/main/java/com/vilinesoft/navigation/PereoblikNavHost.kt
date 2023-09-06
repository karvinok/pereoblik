package com.vilinesoft.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vilinesoft.navigation.destinations.ROUTE_HOME_GRAPH
import com.vilinesoft.navigation.destinations.homeGraph

@Composable
fun PereoblikNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_HOME_GRAPH,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navController,
            nestedGraphs = {
                //other screens in addition to home
            }
        )
    }
}