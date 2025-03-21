package com.example.githubappcompose.uiux

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubappcompose.favorite.FavoriteViewModel
import com.example.githubappcompose.model.Item
import com.example.githubappcompose.user.UserViewModel
import com.google.gson.Gson

@Composable
fun DetailScreen(navController: NavController, usernameGson:String,
                 userViewModel: UserViewModel= hiltViewModel(),
                 modifier: Modifier = Modifier) {
    val detailUser by userViewModel.detailUser.collectAsState()
    val getFollowing by userViewModel.getFollowings.collectAsState()
    val getFollowers by userViewModel.getFollowers.collectAsState()

    val username=Gson().fromJson(usernameGson,Item::class.java)
    userViewModel.detailUser(username.login)
    userViewModel.getFollowings(username.login)
    userViewModel.getFollowers(username.login)
    detailUser?.let { ViewPager(it,getFollowing,getFollowers,navController) }
}

@Composable
fun ViewPager(detailUser:Item, getFollowing: List<Item>, getFollowers: List<Item>, navController: NavController, favoriteViewModel: FavoriteViewModel= hiltViewModel(), modifier: Modifier=Modifier) {
    val tabList= listOf("Followings","Followers")
    val pagerState= rememberPagerState(
        initialPage = 0,
        pageCount = { tabList.size }
    )
    var targetPage by remember { mutableStateOf(-1) }

    val getFavoriteId by favoriteViewModel.getFavoriteId.collectAsState()
    favoriteViewModel.getFavoriteId(id = detailUser.id.toString())

    Column(modifier.padding(20.dp)) {
        Row(modifier = Modifier.padding(start = 120.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            DetailCard(detailUser)
            IconButton(onClick = {
                if (getFavoriteId==null){
                    favoriteViewModel.addFavorite(detailUser)
                }
                else{
                    favoriteViewModel.deleteavorite(detailUser.id.toString())
                }
            },modifier.size(48.dp)) {
                Icon(Icons.Default.Favorite,null,
                    tint = if (getFavoriteId!=null) Color.Red else Color.Gray)
            }
        }
        ScrollableTabRow(
            pagerState = pagerState,
            tabList = tabList,
            onTargetPage = {index->
                targetPage=index
            }
        )
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) {page->
            when(page){
                0->FollowingPage(getFollowing,navController)
                1->FollowersPage(getFollowers,navController)
            }
        }
    }
    if (targetPage!=-1){
        LaunchedEffect(targetPage) {
            pagerState.scrollToPage(targetPage)
            targetPage=-1
        }
    }
}

@Composable
fun FollowingPage(getFollowing: List<Item>, navController: NavController) {
    LazyColumn {
        items(getFollowing){
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AsyncImage(model = it.avatar_url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(shape = CircleShape).size(75.dp).clickable {
                        val user= Gson().toJson(it)
                        val userEncoded= Uri.encode(user)
                        navController.navigate("Details/$userEncoded")
                    }
                )
                Text(it.login)
            }
        }
    }
}

@Composable
fun FollowersPage(getFollowers: List<Item>, navController: NavController) {
    LazyColumn {
        items(getFollowers){
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AsyncImage(model = it.avatar_url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(shape = CircleShape).size(75.dp).clickable {
                        val user= Gson().toJson(it)
                        val userEncoded= Uri.encode(user)
                        navController.navigate("Details/$userEncoded")
                    }
                )
                Text(it.login)
            }
        }
    }
}

@Composable
fun ScrollableTabRow(
    pagerState: PagerState,
    tabList: List<String>,
    onTargetPage : (Int)->Unit
) {
    val horizontalScrollState= rememberScrollState()

    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .horizontalScroll(horizontalScrollState),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        tabList.forEachIndexed { index, s ->
            var isSelected=pagerState.currentPage==index
            Column(modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onTargetPage(index)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
              Text(s)
                if (isSelected) {
                    Box(modifier = Modifier.height(2.dp).width(40.dp).background(Color.Blue))
                }
            }
        }
    }
}

@Composable
fun DetailCard(item: Item,modifier: Modifier = Modifier) {
    Column(modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            AsyncImage(model = item.avatar_url,contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp))
            Text(item.login)
    }
}
