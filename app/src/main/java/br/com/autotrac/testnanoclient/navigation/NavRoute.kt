package br.com.autotrac.testnanoclient.navigation
sealed class NavRoute(val path: String) {

    object Login: NavRoute("login")

    object Home: NavRoute("home")

    object Inbox: NavRoute("inbox") {
        val selectedTab = "selectedTab"
        val showDetails = "showDetails"
    }

    object Search: NavRoute("search") {
        const val query = "query"
    }
    object SendMessage: NavRoute("sendMessage") {
        const val query = "query"
    }
    object CheckList: NavRoute("checkList") {
        const val query = "query"
    }
    object Parameters: NavRoute("parameters") {
        const val query = "query"
    }
    object HttpTest: NavRoute("httpTest") {
        const val query = "query"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}

