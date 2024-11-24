package com.example.incidentmanager.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.incidentmanager.network.SocketHelper
import com.example.incidentmanager.repository.ActiuRepository
import com.example.incidentmanager.repository.AuthRepository
import com.example.incidentmanager.repository.IncidenciaRepository
import com.example.incidentmanager.repository.UsuariRepository
import com.example.incidentmanager.ui.composables.LoadingScreen
import com.example.incidentmanager.viewModels.ActiuViewModel
import com.example.incidentmanager.viewModels.AuthViewModel
import com.example.incidentmanager.viewModels.IncidenciaViewModel
import com.example.incidentmanager.viewModels.UsuariViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket

/**
 * author: Xavier Comí
 */

@ExperimentalMaterial3Api
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()
    val socketHelper = remember { mutableStateOf<SocketHelper?>(null) }


    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val socket = Socket("10.0.2.2", 12345)
            socketHelper.value = SocketHelper(socket)
        }
    }

    if (socketHelper.value == null) {
        LoadingScreen()
    } else {

        socketHelper.value?.let { initializedSocketHelper ->
            // Repositories
            val authRepository = remember { AuthRepository() }
            val usuariRepository = remember { UsuariRepository(initializedSocketHelper) }
            val actiuRepository = remember { ActiuRepository(initializedSocketHelper) }
            val incidenciaRepository = remember { IncidenciaRepository(initializedSocketHelper) }

            // ViewModels
            val authViewModel = remember { AuthViewModel(authRepository) }
            val usuariViewModel = remember { UsuariViewModel(usuariRepository) }
            val actiuViewModel = remember { ActiuViewModel(actiuRepository) }
            val incidenciaViewModel = remember { IncidenciaViewModel(incidenciaRepository) }

            NavHost(navController = navController, startDestination = "login_screen") {
                composable("login_screen") { LoginScreen(navController, authViewModel) }
                composable("register_screen") { RegisterScreen(navController, authViewModel) }
                composable("home_screen") { HomeScreen(navController, usuariViewModel) }
                composable("usuari_screen") { UsuariScreen(navController, usuariViewModel) }
                composable("actiu_screen") { ActiuScreen(navController, actiuViewModel) }
                composable("incidencia_screen") {
                    IncidenciaScreen(
                        navController,
                        incidenciaViewModel
                    )
                }
            }
        }
    }
}


