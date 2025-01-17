package dev.bugstitch.titanote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.TitanoteViewModel
import dev.bugstitch.titanote.ui.theme.ZenColors
import dev.bugstitch.titanote.utils.Logos

@Composable
fun EditComponent(modifier: Modifier = Modifier,
                  viewModel: TitanoteViewModel){

    Column(modifier = modifier.fillMaxSize().padding(8.dp)
        .imePadding()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()
            .imePadding()
            .background(color = ZenColors.NoteColors.colorList[viewModel.noteColor.value], shape = RoundedCornerShape(15.dp))
            .defaultMinSize(minHeight = 400.dp)) {
            item {
                LogoButton(icon = Logos[viewModel.noteLogo.value], static = true, size = 64.dp) {
                    viewModel.setSelectNoteLogoState(!viewModel.selectNoteLogoState.value)
                }
            }
            item {
                TextField(value = viewModel.noteTitle.value,
                    onValueChange = {
                    viewModel.setNoteTitle(it)
                },
                    placeholder = { Text(stringResource(R.string.title),
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = ZenColors.EerieBlack,
                        ),
                    textStyle = TextStyle.Default.copy(color = ZenColors.Night,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                )
            }
            item {

                TextField(value = viewModel.noteContent.value, onValueChange = {
                    viewModel.setNoteContent(it)
                },
                    textStyle = TextStyle.Default.copy(color = ZenColors.Night),
                    placeholder = { Text(stringResource(R.string.content),
                        color = Color.DarkGray) },
                    modifier = Modifier.fillMaxWidth().padding(top = 0.dp),
                    colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = ZenColors.Night,
                        unfocusedTextColor = ZenColors.Night,
                        disabledTextColor = ZenColors.Night,
                        cursorColor = ZenColors.EerieBlack)
                )
            }
        }
    }
}