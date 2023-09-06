package com.vilinesoft.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.vilinesoft.documents.DocumentsRoute
import com.vilinesoft.handbook.HandbookRoute
import com.vilinesoft.home.HomeRoute

const val ROUTE_HOME_GRAPH = "route_home_graph"
const val ROUTE_HOME = "route_home"
const val ROUTE_HANDBOOK = "route_handbook"
const val ROUTE_DOCUMENTS = "route_documents"

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = ROUTE_HOME_GRAPH,
        startDestination = ROUTE_HOME,
    ) {
        composable(route = ROUTE_HOME) {
            HomeRoute(
                onHandbookClick = navController::navigateToHandbook,
                onDocumentsClick = navController::navigateToDocuments,
                onSettingsClick = {},
            )
        }
        dialog(route = ROUTE_HANDBOOK) {
            HandbookRoute(onDismissRequest = navController::popBackStack)
        }
        composable(route = ROUTE_DOCUMENTS) {
            DocumentsRoute()
        }
        nestedGraphs()
    }
}

val DefaultNavOptions = navOptions {
    launchSingleTop = true
}

fun NavController.navigateToHandbook(navOptions: NavOptions = DefaultNavOptions) {
    this.navigate(ROUTE_HANDBOOK, navOptions)
}

fun NavController.navigateToHome(navOptions: NavOptions = DefaultNavOptions) {
    this.navigate(ROUTE_HOME, navOptions)
}

fun NavController.navigateToDocuments(navOptions: NavOptions = DefaultNavOptions) {
    this.navigate(ROUTE_DOCUMENTS, navOptions)
}
