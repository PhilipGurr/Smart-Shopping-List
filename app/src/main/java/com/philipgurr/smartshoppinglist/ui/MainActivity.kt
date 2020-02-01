package com.philipgurr.smartshoppinglist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.philipgurr.smartshoppinglist.R
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.MITLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices
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
                else -> navController.navigate(id)
            }
            drawerLayout.closeDrawers()
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_shopping_lists), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        about_button.onClick {
            getLicenseDialog().show()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                showUserNotLoggedIn()
            }

        nav_view.menu.findItem(R.id.nav_signout).isVisible = false
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getLicenseDialog(): LicensesDialog {
        val notices = Notices()
        with(notices) {
            addNotice(
                Notice(
                    "Android Support Libraries",
                    "https://developer.android.com/topic/libraries/support-library/index.html",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Kotlin",
                    "https://kotlinlang.org/",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Material Components",
                    "https://material.io/components/",
                    "",
                    MITLicense()
                )
            )
            addNotice(
                Notice(
                    "QuickPermissions Kotlin",
                    "https://github.com/QuickPermissions/QuickPermissions-Kotlin",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Anko",
                    "https://github.com/Kotlin/anko",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Mockito Kotlin",
                    "https://github.com/nhaarman/mockito-kotlin",
                    "",
                    MITLicense()
                )
            )
            addNotice(
                Notice(
                    "Dagger",
                    "https://github.com/google/dagger",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Firebase",
                    "https://github.com/firebase/firebase-android-sdk",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Coil",
                    "https://github.com/coil-kt/coil",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Retrofit",
                    "https://github.com/square/retrofit",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "LicensesDialog",
                    "http://psdev.de",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
            addNotice(
                Notice(
                    "Coil",
                    "https://github.com/coil-kt/coil",
                    "",
                    ApacheSoftwareLicense20()
                )
            )
        }
        return LicensesDialog.Builder(this)
            .setNotices(notices)
            .build()
    }
}
