package dev.bugstitch.titanote.ui.components.startPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.bugstitch.titanote.utils.Navigation

@Composable
fun Animal(navController: NavController)
{
    StartPageLayout(bottomBar = {
        Column(modifier = Modifier.navigationBarsPadding()
            .fillMaxWidth()
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = {
                navController.navigate(Navigation.startPage.PASSWORD)
            },modifier = Modifier.fillMaxWidth()) {
                Text("Next")
            }

        }
    },
        content = {
            Text("Enter your favourite animal")
            Text("You need to remember this!")
            OutlinedTextField(value = "Cat", onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done))
        })
}