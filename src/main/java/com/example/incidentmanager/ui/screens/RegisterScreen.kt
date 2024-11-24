package com.example.incidentmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.incidentmanager.R
import com.example.incidentmanager.common.Area
import com.example.incidentmanager.models.Usuari
import com.example.incidentmanager.utils.status.RegistrationStatus
import com.example.incidentmanager.viewModels.AuthViewModel
import com.example.incidentmanager.viewModels.UsuariViewModel

/**
 * author: Xavier Comí
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var contrasenya by remember { mutableStateOf("") }

    // Observing registration status
    val registrationStatus by viewModel.registrationStatus.collectAsState(initial = null)

    Column (
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        // App title
        Text(
            text = "Incident Manager 1.0",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Welcome message
        Text(
            text = "Welcome!",
            modifier = Modifier.padding(5.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal
        )
        // Email input
        Text(text = "Create your account")
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text( text ="Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Password input
        OutlinedTextField(
            value = contrasenya,
            onValueChange = { contrasenya = it},
            label = { Text(text = "Contrasenya") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Register button
        Button(onClick = {
            viewModel.register(email, contrasenya)
        },
            modifier = Modifier.width(285.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Register")
        }

        // Registration feedback
        registrationStatus?.let {
            when (it) {
                is RegistrationStatus.Success -> {
                    Text(
                        text = "Registration successful! Redirecting to login...",
                        color = Color.Green,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    LaunchedEffect(Unit) {
                        navController.navigate("login_screen") {
                            popUpTo("register_screen") { inclusive = true }
                        }
                    }
                }
                is RegistrationStatus.Error -> {
                    Text(
                        text = "Registration failed: ${(registrationStatus as 
                                RegistrationStatus.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.register),
            contentDescription = "Login image",
            modifier = Modifier.size(300.dp)
        )
        Text(text = " or ")
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Log in",
            modifier = Modifier.clickable { navController.navigate("login_screen") }
        )
    }
}