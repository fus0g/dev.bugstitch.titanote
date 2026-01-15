package dev.bugstitch.titanote.ui.components.startPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.bugstitch.titanote.utils.Navigation

@Composable
fun Landing(navController: NavController)
{
    StartPageLayout(
        bottomBar = {
            Column(modifier = Modifier.navigationBarsPadding()
                .fillMaxWidth()
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                Button(onClick = {
                    navController.navigate(Navigation.startPage.ANIMAL)
                },modifier = Modifier.fillMaxWidth()) {
                    Text("Yes Sure!")
                }
                TextButton(onClick = {}) {
                    Text("No Thanks")
                }

            }
        },
        content = {
            Text("Hello Welcome To Titan Note")
            Text("Do you want to enable encryption?")
        }
    )
}
