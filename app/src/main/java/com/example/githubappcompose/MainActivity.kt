package com.example.githubappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubappcompose.model.Bottom
import com.example.githubappcompose.ui.theme.GithubAppComposeTheme
import com.example.githubappcompose.ui.theme.Purple40
import com.example.githubappcompose.uiux.BottomNavigation
import com.example.githubappcompose.uiux.DetailScreen
import com.example.githubappcompose.uiux.FavoriteScreen
import com.example.githubappcompose.uiux.SearchFavoriteScreen
import com.example.githubappcompose.uiux.SearchScreen
import com.example.githubappcompose.uiux.UserHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAppComposeTheme {
                val navController= rememberNavController()
                var isSearching by remember { mutableStateOf(false) }
                var isExpanded by remember { mutableStateOf(false) }
                var username by remember { mutableStateOf("") }
                val context= LocalContext.current
                val keyboardController= LocalSoftwareKeyboardController.current
                val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope= rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        if (isSearching){
                            TopAppBar(
                                title = { OutlinedTextField(
                                    value = username,
                                    onValueChange = {
                                        username=it
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Search
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            navController.navigate("Search/$username")
                                            keyboardController?.hide()
                                        }
                                    ),
                                    trailingIcon = {
                                        IconButton(onClick = {navController.navigate("Search/$username")}) {
                                            Icon(Icons.Default.Search,null)
                                        }
                                     },
                                    placeholder = { Text("username") }
                                ) },
                                actions = {
                                    IconButton(onClick = {isSearching=false}) {
                                        Icon(Icons.Default.Close,null)
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = {isExpanded=true
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                    }) {
                                        Icon(Icons.Default.Menu,null)
                                    }
                                }
                            )
                        }
                        else{
                            TopAppBar(
                                title = { Text("Github Clone App") },
                                navigationIcon = {
                                    IconButton(onClick ={isExpanded=true
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    } ) {
                                        Icon(Icons.Default.Menu,null)
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {isSearching=true}) {
                                        Icon(Icons.Default.Search,null)
                                    }
                                },
                            )
                        }
                        if (drawerState.isOpen){
                            ModalNavigationDrawer(
                                drawerState = drawerState,
                                gesturesEnabled = true,
                                drawerContent = {
                                    ModalDrawerSheet {
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .background(Color.Green))
                                        NavigationDrawerItem(
                                            label = { Text("Home") },
                                            selected = false,
                                            onClick = {
                                                coroutineScope.launch {
                                                    drawerState.close()
                                                }
                                                navController.navigate("Home"){
                                                    popUpTo(navController.graph.startDestinationId){
                                                        inclusive=true
                                                    }
                                                }
                                            },
                                            icon = { Icon(Icons.Default.Home,null) },
                                            colors = NavigationDrawerItemDefaults.colors(Purple40),
                                        )
                                        NavigationDrawerItem(
                                            label = { Text("Favorite") },
                                            selected = false,
                                            onClick = {
                                                coroutineScope.launch {
                                                    drawerState.close()
                                                }
                                                navController.navigate("Favorite"){
                                                    popUpTo(navController.graph.startDestinationId){
                                                        inclusive=true
                                                    }
                                                }
                                            },
                                            icon = { Icon(Icons.Default.Favorite,null) },
                                            colors = NavigationDrawerItemDefaults.colors(Purple40),
                                        )
                                    }
                                },
                            ) { }
                        }
                    },
                    bottomBar = { BottomNavigation(navController) }) {
                    NavHost(navController,"Home", modifier = Modifier.padding(it)){
                        composable("Home"){
                            UserHomeScreen(navController)
                        }
                        composable("Favorite"){
                            FavoriteScreen(navController)
                        }
                        composable("Details/{username}"
                        , arguments = listOf(navArgument("username",{
                            type= NavType.StringType
                            }))
                        ){args->
                            val username=args.arguments?.getString("username")
                            if (username != null) {
                                DetailScreen(navController,username)
                            }
                        }
                        composable("Search/{username}",
                            arguments = listOf(navArgument("username",{
                                type=NavType.StringType
                            }))
                        ){backStack->
                            val username=backStack.arguments?.getString("username")
                            if (username != null) {
                                SearchScreen(navController,username)
                            }
                        }
                        composable("SearchFavorite/{query}",
                            arguments = listOf(navArgument("query",{
                                type=NavType.StringType
                            }))
                        ){
                            val query=it.arguments?.getString("query")
                            if (query != null) {
                                SearchFavoriteScreen(navController,query)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubAppComposeTheme {
        Greeting("Android")
    }
}