package com.xetom.wancool.nav

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.xetom.wancool.nav.DogNav.Arg

interface INavArg

object EmptyArg : INavArg

interface INavPath<ARG : INavArg> {
    val path: String

    fun arguments(): List<NamedNavArgument>

    fun make(arg: ARG): String

    fun unpack(savedState: SavedStateHandle): ARG

    /**
     * check if route is matching
     *
     * @param route 当前的route值
     * @return true表示相等，false表示不相等
     */
    fun matchRoute(route: String?): Boolean {
        if (!route.isNullOrEmpty()) {
            var pathIndex = this.path.indexOf('/')
            if (pathIndex < 0) {
                pathIndex = this.path.indexOf('?')
            }
            if (pathIndex < 0) {
                pathIndex = this.path.length
            }
            return route == this.path.substring(0, pathIndex)
        }
        return false
    }
}

abstract class BaseEmptyArgNavPath : INavPath<EmptyArg> {
    override fun arguments(): List<NamedNavArgument> = emptyList()
    override fun make(arg: EmptyArg): String = this.path
    override fun unpack(savedState: SavedStateHandle): EmptyArg = EmptyArg
}

object HomeNav : BaseEmptyArgNavPath() {
    override val path: String
        get() = "home"
}

object LibraryNav : BaseEmptyArgNavPath() {
    override val path: String
        get() = "library"
}

object DogNav : INavPath<Arg> {

    data class Arg(
        val main: String,
        val sub: String,
    ) : INavArg

    override val path: String
        get() = "dog/{main}/{sub}"

    override fun arguments(): List<NamedNavArgument> = listOf(
        navArgument("main") { type = NavType.StringType },
        navArgument("sub") { type = NavType.StringType },
    )

    override fun make(arg: Arg): String = "dog/${arg.main}/${arg.sub}"

    override fun unpack(savedState: SavedStateHandle): Arg = Arg(
        main = savedState.get<String>("main").orEmpty(), sub = savedState.get<String>("sub").orEmpty()
    )

}