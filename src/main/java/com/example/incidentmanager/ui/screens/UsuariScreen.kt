package com.example.incidentmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.incidentmanager.R
import com.example.incidentmanager.ui.composables.UserCard
import com.example.incidentmanager.viewModels.UsuariViewModel

/**
 * @author Xavier Comí
 */

@ExperimentalMaterial3Api
@Composable
fun UsuariScreen (navController: NavHostController, viewModel: UsuariViewModel) {

    var expanded by remember { mutableStateOf(false) }
    var query: String by rememberSaveable { mutableStateOf("") }
    val filteredUsers by viewModel.filteredUsuaris.collectAsState() // Observador de la lista sencera d'usuaris

    LaunchedEffect(Unit) {
        viewModel.fetchAllUsers()
    }

    Column (
        modifier = Modifier
            .fillMaxSize().padding( top = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box( modifier = Modifier.fillMaxWidth().padding(10.dp) ) {
            Image (
                painter = painterResource(id = R.drawable.usuari),
                contentDescription = "UsuariScreen image",
                contentScale = ContentScale.Inside,
                modifier = Modifier.padding(start = 25.dp, end = 25.dp)
            )
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .size(35.dp)
                    .align(alignment = Alignment.TopStart)
            ) {
                Icon(
                    painterResource(id = R.drawable.bar3),
                    contentDescription = "Menú desplegable amb opció logout",
                )
            }
            IconButton(
                onClick = { navController.navigate("home_screen") },
                modifier = Modifier
                    .size(30.dp)
                    .align(alignment = Alignment.TopEnd)
            ) {
                Icon(
                    painterResource(id = R.drawable.home_icon),
                    contentDescription = "Home icon"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White),
            ) {
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(text = "My account") },
                    leadingIcon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "Account icon") },
                    onClick = {
                    },
                )
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(text = "FAQ") },
                    leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = "Info icon") },
                    onClick = {
                    },
                )
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(text = "email") },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "Email icon") },
                    onClick = {
                    },
                )
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(text = "settings") },
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings icon") },
                    onClick = {
                    },
                )
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(text = "logout") },
                    leadingIcon = { Icon(Icons.Outlined.Clear, contentDescription = "Logout icon") },
                    onClick = {
                        viewModel.clearLoginState()
                        navController.navigate("login_screen")
                    }
                )
            }
        }

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            text = "Usuaris",
            fontSize = 20.sp,
            color = Color.Black,
        )

        TextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
                viewModel.onQueryChange(newQuery)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedPlaceholderColor = Color.Gray,
                unfocusedContainerColor =  Color(0xFFFCE9E3)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, 10.dp, end = 20.dp, 20.dp),
            placeholder = { Text(text = stringResource(R.string.hint_search_query)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search icon"
                )
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { items(filteredUsers) { user -> UserCard(user) } }
    }
}