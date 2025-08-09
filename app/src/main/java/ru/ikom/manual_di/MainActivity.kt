package ru.ikom.manual_di

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature_detail_message.presentation.DetailMessageFragment
import ru.ikom.feature_messages.presentation.MessagesFragment
import ru.ikom.manual_di.app.App
import ru.ikom.manual_di.navigation.AnimateScreen
import ru.ikom.manual_di.navigation.BaseFragmentFactory

class MainActivity : AppCompatActivity() {

    private val component: RootComponent by viewModels {
        RootComponent.factory((application as App).appContainer)
    }

    private val fragmentFactoryImpl = FragmentFactoryImpl()

    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        object : AppNavigator(
            activity = this,
            containerId = R.id.content,
            fragmentManager = supportFragmentManager,
            fragmentFactory = fragmentFactoryImpl
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            component.initNavigation()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
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

        fun messagesFragment() =
            MessagesFragment(
                feature = component::messagesFeature
            )

        fun detailMessageFragment() =
            DetailMessageFragment(
                feature = component::detailMessageFeature
            )

    }
}