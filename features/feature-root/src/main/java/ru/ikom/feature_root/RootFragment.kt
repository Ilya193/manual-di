package ru.ikom.feature_root

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature_detail_message.presentation.DetailMessageFragment
import ru.ikom.feature_messages.presentation.MessagesFragment
import ru.ikom.feature_root.component.RootComponent
import ru.ikom.feature_root.component.RootFeature
import ru.ikom.feature_root.component.createRootComponent
import ru.ikom.ui.navigation.AnimateScreen
import ru.ikom.ui.navigation.BaseFragmentFactory

class RootFragment(
    private val feature: () -> RootFeature,
) : Fragment(R.layout.root_fragment) {

    private val component: RootComponent by viewModels {
        createRootComponent(feature)
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
            when (clasz) {
                MessagesFragment::class.java -> messagesFragment() as T
                DetailMessageFragment::class.java -> detailMessageFragment() as T
                else -> throw NotImplementedError()
            }

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            get(loadFragmentClass(classLoader, className))

        fun messagesFragment() =
            MessagesFragment(
                feature = component::messagesFeature,
            )

        fun detailMessageFragment() =
            DetailMessageFragment(
                feature = component::detailMessageFeature,
            )
    }
}