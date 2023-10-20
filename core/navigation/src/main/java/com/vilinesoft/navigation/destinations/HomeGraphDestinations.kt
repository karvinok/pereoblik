package com.vilinesoft.navigation.destinations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.get
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.vilinesoft.document_edit.DocumentEditScreen
import com.vilinesoft.documents.DocumentsScreen
import com.vilinesoft.handbook.HandbookScreen
import com.vilinesoft.home.HomeScreen
import com.vilinesoft.settings.SettingsScreen

const val ROUTE_HOME_GRAPH = "route_home_graph"
const val ROUTE_HOME = "route_home"
const val ROUTE_HANDBOOK = "route_handbook"
const val ROUTE_DOCUMENTS = "route_documents"
const val ROUTE_DOCUMENT_EDIT = "route_document_edit"
const val ROUTE_SETTINGS = "route_settings"

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = ROUTE_HOME_GRAPH,
        startDestination = ROUTE_HOME,
    ) {
        animComposable(route = ROUTE_HOME) {
            HomeScreen(
                onHandbookClick = navController::navigateToHandbook,
                onDocumentsClick = navController::navigateToDocuments,
                onSettingsClick = navController::navigateToSettings
            )
        }
        dialog(route = ROUTE_HANDBOOK) {
            HandbookScreen(onCloseRequest = navController::popBackStack)
        }
        animComposable(route = ROUTE_DOCUMENTS) {
            DocumentsScreen(onNavigateDocument = navController::navigateToDocumentEdit)
        }
        animComposable(route = ROUTE_SETTINGS) {
            SettingsScreen()
        }
        animComposable(
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

public fun NavGraphBuilder.animComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(300)
            ) + fadeIn()
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(300)
            ) + fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(300)
            ) + fadeIn()
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(300)
            ) + fadeOut()
        },
        content = content
    )
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
fun NavController.navigateToSettings( navOptions: NavOptions = DefaultNavOptions) {
    navigate("$ROUTE_SETTINGS", navOptions)
}
