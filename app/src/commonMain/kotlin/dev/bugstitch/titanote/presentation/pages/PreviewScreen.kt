package dev.bugstitch.titanote.presentation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.LogoButton
import dev.bugstitch.titanote.presentation.components.SideBar
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.presentation.theme.ZenColors
import dev.bugstitch.titanote.presentation.viewmodels.TitanoteViewModel
import dev.bugstitch.titanote.utils.LogoString
import dev.bugstitch.titanote.utils.Logos
import dev.bugstitch.titanote.utils.Navigation
import dev.bugstitch.titanote.utils.TopBarState
import dev.bugstitch.titanote.utils.formatForUi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PreviewScreen(viewModel: TitanoteViewModel, navController: NavController) {
    BackHandler {
        if(viewModel.sideMenuOpen.value)
        {
            viewModel.openSideMenu(false)
        }
        else{
            navController.navigate(Navigation.HOME)
            viewModel.emptyCurrent()
        }
    }

    viewModel.setTopBarState(TopBarState.Preview)

    Scaffold(topBar = {
        TopBar(viewModel,
            onDelete = {
                navController.navigate(Navigation.HOME)
            })
    },
        floatingActionButton = {
            AddButton(Lucide.Pen) {
                navController.navigate(Navigation.EDIT_NOTE)
            }
        }) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)
            .fillMaxWidth()) {
            val currentNote = viewModel.getCurrentNote()
            item {
                AnimatedVisibility(currentNote != null)
                {
                    Column(modifier = Modifier.padding(8.dp)
                        .background(ZenColors.NoteColors.colorList[viewModel.noteColor.value], shape = RoundedCornerShape(15.dp))
                        .defaultMinSize(minHeight = 400.dp),
                            verticalArrangement = Arrangement.SpaceBetween) {

                        SelectionContainer {
                            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp).fillMaxWidth()) {
                                LogoButton(icon = Logos[viewModel.noteLogo.value], contentDescription = LogoString[viewModel.noteLogo.value], static = true, size = 64.dp) { }
                                Text(text = viewModel.noteTitle.value,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = ZenColors.Night
                                )
                                Text(text = viewModel.noteContent.value,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = ZenColors.Night,
                                    fontSize = 18.sp)
                            }
                        }

                        Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp)
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.End){


                            currentNote?.let { note ->
                                SelectionContainer {
                                    Text(
                                        text = note.date.formatForUi(),
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Box(Modifier.height(64.dp))
            }

        }

    }
    SideBar(viewModel)
}