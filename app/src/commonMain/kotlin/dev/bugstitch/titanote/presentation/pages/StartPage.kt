package dev.bugstitch.titanote.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.bugstitch.titanote.presentation.components.startPage.Animal
import dev.bugstitch.titanote.presentation.components.startPage.Landing
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.Navigation

@Composable
fun StartPage(viewModel: TitanoteViewModel){

    val checked = remember{ mutableStateOf(false) }

    val navController = rememberNavController()

    Scaffold(
    ) {contentPadding->
        Column(modifier = Modifier.padding(contentPadding)) {

            NavHost(navController = navController, startDestination = Navigation.startPage.START){

                composable(Navigation.startPage.START) {
                    Landing(navController)
                }
                composable(Navigation.startPage.ANIMAL) {
                    Animal(navController)
                }
                composable(Navigation.startPage.PASSWORD) {
                    Scaffold(
                        bottomBar = {
                            Column(modifier = Modifier.navigationBarsPadding()
                                .fillMaxWidth()
                                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                                Button(onClick = {

                                },modifier = Modifier.fillMaxWidth(), enabled = false) {
                                    Text("Done")
                                }

                            }
                        }
                    ){innerPadding->

                        Column(Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Enter a strong password")
                            OutlinedTextField(value = "Cat", onValueChange = {},
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),)
                            OutlinedTextField(value = "Cat", onValueChange = {},
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),)
                        }

                    }
                }
            }
        }
    }




}