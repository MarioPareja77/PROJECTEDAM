package com.example.incidentmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.incidentmanager.R
import com.example.incidentmanager.viewModels.AuthViewModel


/**
 * author: Xavier Comí
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authResult by viewModel.authResult.observeAsState()
    val isConnected by viewModel.isConnected.observeAsState()

    // Connect to the server when the screen loads
    LaunchedEffect(Unit) {
        viewModel.connectToServer("10.0.2.2", 12345)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
        Text(
            text = "Welcome Back",
            modifier = Modifier.padding(5.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal
        )
        Text(text = "Login to your account")
        Spacer(modifier = Modifier.height(25.dp))
        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text( text ="email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Login button
        Button(onClick = {
            viewModel.authenticateUser(email, password)
        },
            modifier = Modifier.width(285.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Login")
        }
        // Connection feedback
        Text(
            text = if (isConnected == true) "Conected to server" else "Connecting...",
            modifier = Modifier.padding(top = 16.dp),
            color = if (isConnected == true) Color.Green else Color.Gray
        )

        // Authentication feedback
        authResult?.let { success ->
            if (success) {
                Text(text = "Login successful!", color = Color.Green)
                // Navigate to home screen on success
                LaunchedEffect(Unit) {
                    navController.navigate("home_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                }
            } else {
                Text(text = "Invalid credentials, please try again", color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Login illustration
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login image",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Create account link
        Text(
            text = "Create account",
            modifier = Modifier.clickable { navController.navigate("register_screen") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Social media icons to sign in
        Text(text = "Or sign in with")
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(100.dp, 20.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook icon",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            )
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google icon",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            )
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "x icon",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        // Forgot password link
        Text(text = "Forgot Password?", fontSize = 12.sp, modifier = Modifier.clickable {})
    }
}
