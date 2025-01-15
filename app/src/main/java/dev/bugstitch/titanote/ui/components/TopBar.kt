package dev.bugstitch.titanote.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.utils.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: TitanoteViewModel){
    Column(modifier = Modifier.fillMaxWidth()){
        TopAppBar(title = { Text(stringResource(R.string.app_name)) },
            actions = {
                when(viewModel.topBarState.value)
                {
                    TopBarState.Home ->{
                        IconButton(onClick = {
                            viewModel.setSearchState(!viewModel.searchState.value)
                        }) {
                            if(!viewModel.searchState.value)
                            {
                                Icon(Icons.Default.Search, contentDescription = "")
                            }else{
                                Icon(Icons.Default.Close, contentDescription = "")
                            }
                        }
                    }
                    TopBarState.Create -> TODO()
                    TopBarState.Preview -> TODO()
                }
            },
            navigationIcon = {
                IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "") }

            })
       AnimatedVisibility(viewModel.searchState.value, enter = expandVertically()) {
           OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = viewModel.searchText.value,
               onValueChange = {
               viewModel.setSearchText(it)
           })
       }
    }
}