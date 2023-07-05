package com.example.nanoclientkotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nanoclientkotlin.screens.CheckListScreen
import com.example.nanoclientkotlin.screens.HomeScreen
import com.example.nanoclientkotlin.screens.InboxScreen
import com.example.nanoclientkotlin.screens.LoginScreen
import com.example.nanoclientkotlin.screens.SendMessageScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.path
    ) {
        addLoginScreen(navController, this)

        addHomeScreen(navController, this)

        addProfileScreen(navController, this)

        addSendMessageScreen(navController, this)

        addCheckListScreen(navController, this)

        addSearchScreen(navController, this)
    }
}

private fun addLoginScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Login.path) {
        LoginScreen(
            navigateToHome = {
                navController.navigate(NavRoute.Home.path)
            }
        )
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    //messageViewModel: MessageViewModel
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {

        HomeScreen(
            navigateToProfile = { id, showDetails ->
                navController.navigate(NavRoute.Inbox.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToSendMessage = { query ->
                navController.navigate(NavRoute.SendMessage.withArgs(query))
            },
            navigateToCheckList = { query ->
                navController.navigate(NavRoute.CheckList.withArgs(query))
            },
            navigateToSearch = { query ->
                navController.navigate(NavRoute.Search.withArgs(query))
            },
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

private fun popUpToLogin(navController: NavHostController) {
    navController.popBackStack(NavRoute.Login.path, inclusive = false)
}

private fun addProfileScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Inbox.withArgsFormat(NavRoute.Inbox.id, NavRoute.Inbox.showDetails),
        arguments = listOf(
            navArgument(NavRoute.Inbox.id) {
                type = NavType.IntType
            }
            ,
            navArgument(NavRoute.Inbox.showDetails) {
                type = NavType.BoolType
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        InboxScreen(
            id = args?.getInt(NavRoute.Inbox.id)!!,
            showDetails = args.getBoolean(NavRoute.Inbox.showDetails),
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}
private fun addSendMessageScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.SendMessage.withArgsFormat(NavRoute.SendMessage.query),
        arguments = listOf(
            navArgument(NavRoute.SendMessage.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        SendMessageScreen(
            query = args?.getString(NavRoute.SendMessage.query),
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) },
            onSendMessage = {message ->
                // Handle sending the message
                // e.g., call an API or perform any desired action
                println("Sending message: $message")
            }
        )
    }
}
private fun addCheckListScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.CheckList.withArgsFormat(NavRoute.CheckList.query),
        arguments = listOf(
            navArgument(NavRoute.CheckList.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        CheckListScreen(
            query = args?.getString(NavRoute.CheckList.query),
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

private fun addSearchScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Search.withArgsFormat(NavRoute.Search.query),
        arguments = listOf(
            navArgument(NavRoute.Search.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

//        SearchScreen(
//            query = args?.getString(NavRoute.Search.query),
//            popBackStack = { navController.popBackStack() },
//            popUpToLogin = { popUpToLogin(navController) }
//        )
    }
}