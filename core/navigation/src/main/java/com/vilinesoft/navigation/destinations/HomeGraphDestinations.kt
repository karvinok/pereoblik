package com.vilinesoft.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.vilinesoft.document_edit.DocumentEditScreen
import com.vilinesoft.documents.DocumentsScreen
import com.vilinesoft.handbook.HandbookScreen
import com.vilinesoft.home.HomeScreen

const val ROUTE_HOME_GRAPH = "route_home_graph"
const val ROUTE_HOME = "route_home"
const val ROUTE_HANDBOOK = "route_handbook"
const val ROUTE_DOCUMENTS = "route_documents"
const val ROUTE_DOCUMENT_EDIT = "route_document_edit"

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = ROUTE_HOME_GRAPH,
        startDestination = ROUTE_HOME,
    ) {
        composable(route = ROUTE_HOME) {
            HomeScreen(
                onHandbookClick = navController::navigateToHandbook,
                onDocumentsClick = navController::navigateToDocuments,
                onSettingsClick = {}
            )
        }
        dialog(route = ROUTE_HANDBOOK) {
            HandbookScreen(onCloseRequest = navController::popBackStack)
        }
        composable(route = ROUTE_DOCUMENTS) {
            DocumentsScreen(onNavigateDocument = navController::navigateToDocumentEdit)
        }
        composable(
            route = "$ROUTE_DOCUMENT_EDIT/{document_id}",
            arguments = listOf(navArgument("document_id") {
                nullable = true
                defaultValue = null
            })
        ) {
            DocumentEditScreen(onCloseRequest = navController::popBackStack)
        }
        nestedGraphs()
    }
}

val DefaultNavOptions = navOptions {
    launchSingleTop = true
}

fun NavController.navigateToHandbook(navOptions: NavOptions = DefaultNavOptions) {
    navigate(ROUTE_HANDBOOK, navOptions)
}

fun NavController.navigateToHome(navOptions: NavOptions = DefaultNavOptions) {
    navigate(ROUTE_HOME, navOptions)
}

fun NavController.navigateToDocuments(navOptions: NavOptions = DefaultNavOptions) {
    navigate(ROUTE_DOCUMENTS, navOptions)
}

fun NavController.navigateToDocumentEdit(documentId: String, navOptions: NavOptions = DefaultNavOptions) {
    navigate("$ROUTE_DOCUMENT_EDIT/$documentId", navOptions)
}
