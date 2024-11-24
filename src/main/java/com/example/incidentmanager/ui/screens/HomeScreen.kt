package com.example.incidentmanager.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.incidentmanager.R
import com.example.incidentmanager.ui.composables.ButtonMenu
import com.example.incidentmanager.viewModels.UsuariViewModel


/**
 * author: Xavier Comí
 */

@ExperimentalMaterial3Api
@Composable
fun HomeScreen (navController: NavHostController, viewModel: UsuariViewModel) {

    var expanded by remember { mutableStateOf(false) }
    var expandedCard by remember { mutableStateOf<String?>(null)}

    val rotationStateIncidencies by animateFloatAsState(
        targetValue = if(expandedCard == "Incidencies") 180f else 0f)
    val rotationStateActius by animateFloatAsState(
        targetValue = if(expandedCard == "Actius") 180f else 0f)
    val rotationStateUsuaris by animateFloatAsState(
        targetValue = if(expandedCard == "Usuaris") 180f else 0f)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
         ) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(35.dp).align(alignment = Alignment.TopStart)
            ) {
                Icon(
                    painterResource(id = R.drawable.bar3),
                    contentDescription = "Menú desplegable amb opció logout"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White) ,
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
                        navController.navigate("login_screen") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(25.dp),
            text = "Incident Manager 1.0",
            fontSize = 20.sp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image (
            painter = painterResource(id = R.drawable.home),
            contentDescription = "HomeScreen image",
            modifier = Modifier.size(200.dp),
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Card Incidències 
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
                )
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {
                expandedCard = if (expandedCard == "Incidencies") null else "Incidencies"
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 10.dp),
                        text = "Incidencies",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .rotate(rotationStateIncidencies),
                        onClick = {
                            expandedCard = if (expandedCard == "Incidencies") null else "Incidencies"}
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrow_down),
                            contentDescription = "Icon arrow down",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
            if (expandedCard == "Incidencies") {
                ButtonMenu("Crea incidències", onClick = { navController.navigate("incidencia_screen")})
                ButtonMenu("Llista incidències", onClick = { navController.navigate("incidencia_screen")})
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // Card Actius
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
                )
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = {
                expandedCard = if (expandedCard == "Actius") null else "Actius"
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 10.dp),
                        text = "Actius",
                        fontSize = 18.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .rotate(rotationStateActius),
                        onClick = {
                            expandedCard = if (expandedCard == "Actius") null else "Actius"
                        }
                    )   {
                        Icon(
                            painterResource(id = R.drawable.arrow_down),
                            contentDescription = "Icon arrow down",
                            modifier = Modifier.size(15.dp),
                        )
                    }
                }
            }
            if (expandedCard == "Actius") {
                ButtonMenu("Crea actiu", onClick = { navController.navigate("actiu_screen")})
                ButtonMenu("Llista actius", onClick = { navController.navigate("actiu_screen")})
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // Card Usuaris
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
                )
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {
                expandedCard = if (expandedCard == "Usuaris") null else "Usuaris"
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 10.dp),
                        text = "Usuaris",
                        fontSize = 18.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .rotate(rotationStateUsuaris),
                        onClick = {
                            expandedCard = if (expandedCard == "Usuaris") null else "Usuaris"
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrow_down),
                            contentDescription = "Icon arrow down",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
            if (expandedCard == "Usuaris") {
                ButtonMenu("Llista usuaris", onClick = { navController.navigate("usuari_screen")})
            }
        }
    }
}
