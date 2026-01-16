package dev.bugstitch.titanote.presentation.components.startPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StartPageLayout(content:@Composable () -> Unit,bottomBar:@Composable () -> Unit)
{
    Scaffold(
        bottomBar = bottomBar
    ){innerPadding->

        Column(
            Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
           content()
        }

    }
}