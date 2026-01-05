package ru.ikom.feature_root

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature_detail_message.api.BaseDetailMessageFragment
import ru.ikom.feature_messages.api.BaseMessagesFragment
import ru.ikom.feature_root.component.RootComponent
import ru.ikom.feature_root.component.RootFeature
import ru.ikom.feature_root.component.RootFeatureDependencies
import ru.ikom.feature_root.component.createRootComponent
import ru.ikom.ui.navigation.AnimateScreen
import ru.ikom.ui.navigation.BaseFragmentFactory
import ru.ikom.ui.navigation.defaultFragmentScreen

abstract class BaseRootFragment(layout: Int) : Fragment(layout)

interface RootFeatureScreen {
    val tag: String

    fun launch(): FragmentScreen

    fun content(feature: () -> RootFeature): BaseRootFragment
}

fun defaultRootScreen(
    deps: () -> RootFeatureDependencies
) =
    object : RootFeatureScreen {

        override val tag: String get() = RootFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultFragmentScreen {
                it.get(RootFragment::class.java)
            }

        override fun content(feature: () -> RootFeature): BaseRootFragment =
            RootFragment(feature, deps)
    }

class RootFragment(
    private val feature: () -> RootFeature,
    private val deps: () -> RootFeatureDependencies,
) : BaseRootFragment(R.layout.root_fragment) {

    private val component: RootComponent by viewModels {
        createRootComponent(feature, deps)
    }

    private val fragmentFactory = FragmentFactoryImpl()

    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        object : AppNavigator(
            activity = requireActivity(),
            containerId = R.id.root_content,
            fragmentManager = childFragmentManager,
            fragmentFactory = fragmentFactory
        ) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                if (screen is AnimateScreen) {
                    fragmentTransaction.setCustomAnimations(
                        screen.enter,
                        screen.exit,
                        screen.popEnter,
                        screen.popExit
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (childFragmentManager.backStackEntryCount > 0) {
                        component.onBack()
                    }
                    else {
                        requireActivity().finish()
                    }
                }
            })

        component.initNavigation(isFirst = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        component.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        component.navigationHolder.removeNavigator()
        super.onPause()
    }

    @Suppress("UNCHECKED_CAST")
    private inner class FragmentFactoryImpl : BaseFragmentFactory() {

        override fun <T : Fragment> get(clasz: Class<T>): T =
            when (clasz.simpleName) {
                component.rootFeatureDependencies.messagesFeatureScreen.tag -> messagesFragment() as T
                component.rootFeatureDependencies.detailMessageFeatureScreen.tag -> detailMessageFragment() as T
                else -> throw NotImplementedError("no impl $clasz")
            }

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            get(loadFragmentClass(classLoader, className))

        fun messagesFragment() =
            component.rootFeatureDependencies.messagesFeatureScreen.content(
                feature = component::messagesFeature,
            )

        fun detailMessageFragment() =
            component.rootFeatureDependencies.detailMessageFeatureScreen.content(
                feature = component::detailMessageFeature,
            )
    }
}