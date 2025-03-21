package com.example.githubappcompose.uiux

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubappcompose.model.Item
import com.example.githubappcompose.user.UserViewModel
import com.google.gson.Gson
import android.net.Uri

@Composable
fun UserHomeScreen(navController: NavController, userViewModel: UserViewModel= hiltViewModel(),
               modifier: Modifier = Modifier) {
    val searchUser by userViewModel.searchUser.collectAsState()
    var q="q"
    userViewModel.searchUser(q)
    
    LazyColumn(modifier.padding(20.dp)) {
        items(searchUser){item->
            UserCard(
                item = item,
                onClick = {
                    val user= Gson().toJson(item)
                    val userEncoded=Uri.encode(user)
                    navController.navigate("Details/$userEncoded")
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun UserCard(item: Item,onClick :()->Unit,modifier: Modifier = Modifier) {
    Card(modifier.padding(20.dp).fillMaxWidth().clickable {
        onClick.invoke()
    },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(20.dp),
    ) {
        Row(modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AsyncImage(model = item.avatar_url,contentDescription = null,
                contentScale = ContentScale.Crop)
            Text(item.login)
        }
    }
}