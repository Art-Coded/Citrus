package com.example.citrusapp.Main.BottomNav

import BottomNavItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.citrusapp.Main.Account.AccountScreen
import com.example.citrusapp.Main.Home.AgriScreen
import com.example.citrusapp.Main.Home.CCISScreen
import com.example.citrusapp.Main.Home.CEAScreen
import com.example.citrusapp.Main.Home.CriminologyScreen
import com.example.citrusapp.Main.Home.EducScreen
import com.example.citrusapp.Main.Home.GradScreen
import com.example.citrusapp.Main.Home.HomeScreen
import com.example.citrusapp.Main.Home.ManagementScreen
import com.example.citrusapp.Main.Home.NursingScreen
import com.example.citrusapp.Main.Home.Shortcuts
import com.example.citrusapp.Main.Inbox.InboxScreen
import com.example.citrusapp.Main.LMS.LMSScreen
import com.example.citrusapp.Main.LMS.MyCourses.AddCourseScreen
import com.example.citrusapp.Main.Network.NetworkScreen

sealed class NavItem(val route: String, val label: String, val lottieIcon: String?) {
    object Home : NavItem("home", "Home", "home")
    object LMS : NavItem("lms", "LMS", "article_icon")
    object Network : NavItem("network", "Network", "newspaper")
    object Inbox : NavItem("inbox", "Inbox", "inbox")
    object Account : NavItem("account", "Account", null)
}

@Composable
fun BottomNavScreen() {
    val navController = rememberNavController()
    val isDarkTheme = isSystemInDarkTheme()

    val items = listOf(
        NavItem.Home,
        NavItem.LMS,
        NavItem.Network,
        NavItem.Inbox,
        NavItem.Account
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController, items = items, isDarkTheme = isDarkTheme)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp), // or whatever height your nav bar usually is
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
        ) {
            composable("home") { backStackEntry ->
                // Pass the navController to HomeScreen
                HomeScreen(navController = navController)
            }
            composable("lms") {
                LMSScreen(navController = navController)  // Pass the main navController directly
            }
            composable("network") {
                NetworkScreen()
            }
            composable("inbox") {
                InboxScreen()
            }
            composable("account") {
                AccountScreen()
            }


            // Home routes


            //college routes
            composable("cea") {
                CEAScreen(navController = navController)
            }
            composable("education") {
                EducScreen(navController = navController)
            }
            composable("management") {
                ManagementScreen(navController = navController)
            }
            composable("ccis") {
                CCISScreen(navController = navController)
            }
            composable("criminology") {
                CriminologyScreen(navController = navController)
            }
            composable("agriculture") {
                AgriScreen(navController = navController)
            }
            composable("nursing") {
                NursingScreen(navController = navController)
            }
            composable("graduate") {
                GradScreen(navController = navController)
            }


            //LMS routes

            composable("addCourse") {
                AddCourseScreen(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<NavItem>,
    isDarkTheme: Boolean
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // List of bottom nav routes
    val bottomNavRoutes = items.map { it.route }

    // Only show if current route is a bottom nav route
    val showBottomNav = currentRoute in bottomNavRoutes

    AnimatedVisibility(
        visible = showBottomNav,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
        modifier = Modifier.height(60.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.8.dp)
                        .alpha(0.6f)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items.forEach { item ->
                        BottomNavItem(
                            label = item.label,
                            animationFile = item.lottieIcon,
                            isSelected = currentRoute == item.route,
                            isDarkTheme = isDarkTheme
                        ) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            }
        }
    }
}

