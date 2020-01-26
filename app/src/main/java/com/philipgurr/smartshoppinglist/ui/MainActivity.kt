package com.philipgurr.smartshoppinglist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.api.load
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.marcoscg.licenser.Library
import com.marcoscg.licenser.License
import com.marcoscg.licenser.LicenserDialog
import com.philipgurr.smartshoppinglist.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.nav_header_not_logged_in.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


const val RC_SIGN_IN = 1001

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        createAndShowNavigation()
    }

    private fun setUpActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun createAndShowNavigation() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.nav_host_fragment)
        setupNavigationHeader()

        nav_view.setNavigationItemSelectedListener { item ->
            when (val id = item.itemId) {
                R.id.nav_signout -> {
                    signOut()
                }
                R.id.nav_about -> {
                    getLicenseDialog().show()
                }
                else -> navController.navigate(id)
            }
            //drawerLayout.closeDrawers()
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_shopping_lists), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                showUserNotLoggedIn()
            }
        nav_view.menu.add(R.id.nav_signout)

        nav_view.menu.findItem(R.id.nav_signout).isVisible = false
    }

    private fun getLicenseDialog(): LicenserDialog {
        return LicenserDialog(this)
            .setTitle("Licenses")
            .setCustomNoticeTitle("Notices for files:")
            .setLibrary(
                Library(
                    "Android Support Libraries",
                    "https://developer.android.com/topic/libraries/support-library/index.html",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Kotlin",
                    "https://kotlinlang.org/",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Material Components",
                    "https://material.io/components/",
                    License.MIT
                )
            )
            .setLibrary(
                Library(
                    "QuickPermissions Kotlin",
                    "https://github.com/QuickPermissions/QuickPermissions-Kotlin",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Anko",
                    "https://github.com/Kotlin/anko",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Mockito Kotlin",
                    "https://github.com/nhaarman/mockito-kotlin",
                    License.MIT
                )
            )
            .setLibrary(
                Library(
                    "Dagger",
                    "https://github.com/google/dagger",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Firebase",
                    "https://github.com/firebase/firebase-android-sdk",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Coil",
                    "https://github.com/coil-kt/coil",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Retrofit",
                    "https://github.com/square/retrofit",
                    License.APACHE
                )
            )
            .setLibrary(
                Library(
                    "Licenser",
                    "https://github.com/marcoscgdev/Licenser",
                    License.MIT
                )
            )
            .setPositiveButton(
                android.R.string.ok
            ) { dialogInterface, i ->
                // TODO: 11/02/2018
            }
    }

    private fun setupNavigationHeader() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            showUserDetails(user)
            nav_view.menu.findItem(R.id.nav_signout).isVisible = true
        } else {
            showUserNotLoggedIn()
            nav_view.menu.findItem(R.id.nav_signout).isVisible = false
        }
    }

    private fun showUserDetails(user: FirebaseUser?) {
        removeNavigationHeader()
        val header = nav_view.inflateHeaderView(R.layout.nav_header_main)
        user?.let {
            header.userName.text = user.displayName
            header.email.text = user.email
            header.userPic.load(user.photoUrl)
        }
        //navController.popBackStack()
    }

    private fun showUserNotLoggedIn() {
        removeNavigationHeader()
        val header = nav_view.inflateHeaderView(R.layout.nav_header_not_logged_in)
        val loginButton = header.loginButton
        loginButton.onClick {
            login()
        }
    }

    private fun removeNavigationHeader() {
        if (navigationHasHeaders()) {
            nav_view.removeHeaderView(nav_view.getHeaderView(0))
        }
    }

    private fun navigationHasHeaders() = nav_view.headerCount > 0

    private fun login() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                setupNavigationHeader()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
