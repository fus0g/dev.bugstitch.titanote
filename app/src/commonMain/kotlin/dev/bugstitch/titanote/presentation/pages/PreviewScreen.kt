package dev.bugstitch.titanote.presentation.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.presentation.components.AddButton
import dev.bugstitch.titanote.presentation.components.TopBar
import dev.bugstitch.titanote.utils.TopBarState
import dev.bugstitch.titanote.utils.formatForUi

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    note: Note,
    onDelete: () -> Unit,
    onEditClick:()-> Unit,
    onBack:()-> Unit
) {
    val scrollState = rememberScrollState()

    val richTextState = rememberRichTextState()

    LaunchedEffect(note.content){
        richTextState.setMarkdown(note.content)
    }
    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            TopBar(
                topBarState = TopBarState.Preview,
                onDelete = {onDelete()},
                onBackPressed = onBack
            )
        },
        floatingActionButton = {
            AddButton(Lucide.Pen){
                onEditClick()
            }
        }
    ){ innerPadding->

        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(scrollState)) {
            SelectionContainer {
                Text(text = note.date.formatForUi(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            SelectionContainer {
                Text(
                    text = note.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            SelectionContainer {
                RichText(
                    state = richTextState,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}