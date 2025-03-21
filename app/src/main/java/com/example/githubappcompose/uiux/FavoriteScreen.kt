package com.example.githubappcompose.uiux

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubappcompose.favorite.FavoriteViewModel
import com.google.gson.Gson
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteScreen(navController: NavController,
                   favoriteViewModel: FavoriteViewModel= hiltViewModel(),
                   modifier: Modifier = Modifier) {
    val getFavorite by favoriteViewModel.getFavorite.collectAsState()
    val searchFavorite by favoriteViewModel.searchFavorite.collectAsState()

    favoriteViewModel.getFavorite()
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Favorite Page", textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = query,
            onValueChange = {
                query=it
            },
            placeholder = { Text("username") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    navController.navigate("SearchFavorite/$query")
                }
            ),
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
        LazyColumn(modifier.padding(20.dp)) {
            items(getFavorite){
                Row(modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = it.avatar_url,null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .size(76.dp)
                            .clickable {
                                var user= Gson().toJson(it)
                                var userEncoded=Uri.encode(user)
                                navController.navigate("Details/$userEncoded")
                            }
                    )
                    Text(it.login)
                }
            }
        }
    }

}