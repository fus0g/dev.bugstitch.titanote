package dev.bugstitch.titanote.presentation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import dev.bugstitch.titanote.data.Note
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    note: Note
) {
    val scrollState = rememberScrollState()

    val richTextState = rememberRichTextState()

    LaunchedEffect(note.content){
        richTextState.setMarkdown(note.content)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .statusBarsPadding()
        .verticalScroll(scrollState)) {

        SelectionContainer {
            Text(
                text = note.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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