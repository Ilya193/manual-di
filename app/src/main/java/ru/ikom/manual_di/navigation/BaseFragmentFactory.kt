package ru.ikom.manual_di.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseFragmentFactory : FragmentFactory() {

    abstract fun <T: Fragment> get(clasz: Class<T>): T
}

fun defaultFragmentScreen(
    key: String? = null,
    clearContainer: Boolean = true,
    fragmentCreator: Creator<BaseFragmentFactory, Fragment>
) = object : FragmentScreen {
    override val screenKey = key ?: fragmentCreator::class.java.name
    override val clearContainer = clearContainer
    override fun createFragment(factory: FragmentFactory) =
        fragmentCreator.create(factory as BaseFragmentFactory)
}

interface AnimateScreen : FragmentScreen {

    val enter: Int
    val exit: Int
    val popEnter: Int
    val popExit: Int
}

fun defaultAnimationScreen(
    enter: Int,
    exit: Int,
    popEnter: Int,
    popExit: Int,
    key: String? = null,
    clearContainer: Boolean = true,
    fragmentCreator: Creator<BaseFragmentFactory, Fragment>
) = object : AnimateScreen {
    override val screenKey = key ?: fragmentCreator::class.java.name

    override val clearContainer = clearContainer
    override fun createFragment(factory: FragmentFactory) =
        fragmentCreator.create(factory as BaseFragmentFactory)

    override val enter: Int = enter
    override val exit: Int = exit
    override val popEnter: Int = popEnter
    override val popExit: Int = popExit
}