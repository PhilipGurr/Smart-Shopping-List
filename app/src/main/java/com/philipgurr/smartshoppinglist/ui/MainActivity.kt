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
                    AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener {
                            showUserNotLoggedIn()
                        }
                }
                else -> navController.navigate(id)
            }
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_shopping_lists), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavigationHeader() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            showUserDetails(user)
        } else {
            showUserNotLoggedIn()
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
        //navController.popBackStack()
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
