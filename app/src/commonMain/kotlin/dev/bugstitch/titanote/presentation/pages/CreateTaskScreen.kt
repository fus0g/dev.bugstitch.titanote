package dev.bugstitch.titanote.presentation.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.utils.TopBarState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateTaskScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onBackPress: () -> Unit,
    onSavePresses: () -> Unit

){

    BackHandler {
        onBackPress()
    }

    Scaffold(
        topBar = {
            TopBar(
                topBarState = TopBarState.Create,
                onBackPressed = onBackPress,
                onSavePress = onSavePresses
                )
        }
    ) { innerPadding->

        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(start = 16.dp , end = 16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {onTitleChange(it)},
                label = {
                    Text("Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = {onDescriptionChange(it)},
                label = {
                    Text("Description")
                },
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}