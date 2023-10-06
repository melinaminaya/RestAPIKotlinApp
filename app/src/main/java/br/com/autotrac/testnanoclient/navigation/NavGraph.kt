package br.com.autotrac.testnanoclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import br.com.autotrac.testnanoclient.screens.CheckListScreen
import br.com.autotrac.testnanoclient.screens.HomeScreen
import br.com.autotrac.testnanoclient.screens.HttpTestScreen
import br.com.autotrac.testnanoclient.screens.InboxScreen
import br.com.autotrac.testnanoclient.screens.LoginScreen
import br.com.autotrac.testnanoclient.screens.ParametersScreen
import br.com.autotrac.testnanoclient.screens.SendMessageScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.path
    ) {
        addLoginScreen(navController, this)

        addHomeScreen(navController, this)

        addInboxScreen(navController, this)

        addSendMessageScreen(navController, this)

        addCheckListScreen(navController, this)

        addParametersScreen(navController, this)

        addHttpTestScreen(navController, this)

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
            navigateToInbox = { id, showDetails ->
                navController.navigate(NavRoute.Inbox.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToSendMessage = { query ->
                navController.navigate(NavRoute.SendMessage.withArgs(query))
            },
            navigateToCheckList = { query ->
                navController.navigate(NavRoute.CheckList.withArgs(query))
            },
            navigateToParameters = { query ->
                navController.navigate(NavRoute.Parameters.withArgs(query))
            },
            navigateToHttpTest = { query ->
                navController.navigate(NavRoute.HttpTest.withArgs(query))
            },
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

private fun popUpToLogin(navController: NavHostController) {
    navController.popBackStack(NavRoute.Login.path, inclusive = false)
}

private fun addInboxScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Inbox.withArgsFormat(NavRoute.Inbox.selectedTab, NavRoute.Inbox.showDetails),
        arguments = listOf(
            navArgument(NavRoute.Inbox.selectedTab) {
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
            selectedTab = args?.getInt(NavRoute.Inbox.selectedTab)!!,
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
        val senderAccess = MessageSenderAccess()

        SendMessageScreen(
            navigateToInbox = { id, showDetails ->
                navController.navigate(NavRoute.Inbox.withArgs(id.toString(), showDetails.toString()))
            },
            popBackStack = { navController.popBackStack() }
        ) { popUpToLogin(navController) }
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
            popBackStack = { navController.popBackStack() }
        ) { popUpToLogin(navController) }
    }
}
private fun addParametersScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Parameters.withArgsFormat(NavRoute.Parameters.query),
        arguments = listOf(
            navArgument(NavRoute.Parameters.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        ParametersScreen(
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}
private fun addHttpTestScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.HttpTest.withArgsFormat(NavRoute.HttpTest.query),
        arguments = listOf(
            navArgument(NavRoute.HttpTest.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        HttpTestScreen(
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