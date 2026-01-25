package dev.bugstitch.titanote.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.AlignCenter
import com.composables.icons.lucide.AlignJustify
import com.composables.icons.lucide.AlignLeft
import com.composables.icons.lucide.AlignRight
import com.composables.icons.lucide.Bold
import com.composables.icons.lucide.Italic
import com.composables.icons.lucide.List
import com.composables.icons.lucide.ListOrdered
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Strikethrough
import com.composables.icons.lucide.Underline
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditScreenNew(
    addToViewModel:(content: String)-> Unit,
    currentTitle:String,
    onTitleChange:(String)->Unit,
    currentContent:String = "",
    onBack:()->Unit
){
    val state = rememberRichTextState()

    LaunchedEffect(currentContent) {
        if (state.toMarkdown().isEmpty()) {
            state.setMarkdown(currentContent)
        }
    }

    BackHandler {
        onBack()
    }


    val scrollState = rememberScrollState()
    var editorFocused by remember { mutableStateOf(false) }

    val bringIntoViewRequester = remember{ BringIntoViewRequester() }

    var textLayoutResult by remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    val bottomPaddingPx = with(LocalDensity.current) { 1000.dp.toPx() }

    LaunchedEffect(state.toMarkdown()){
        addToViewModel(state.toMarkdown())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        BasicTextField(
            value = currentTitle,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                if (currentTitle.isEmpty()) {
                    Text(
                        "Title...",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Gray
                        )
                    )
                }
                innerTextField()
            },
        )

        LaunchedEffect(state.selection, editorFocused) {
            if (!editorFocused) return@LaunchedEffect

            val layout = textLayoutResult ?: return@LaunchedEffect

            kotlinx.coroutines.delay(16)

            val textLength = layout.layoutInput.text.text.length
            if (textLength == 0) return@LaunchedEffect

            val safeOffset = state.selection.end.coerceIn(0, textLength)

            val cursorRect = layout.getCursorRect(safeOffset)

            val paddedRect = cursorRect.copy(
                bottom = cursorRect.bottom + bottomPaddingPx
            )

            bringIntoViewRequester.bringIntoView(paddedRect)
        }


        RichTextEditor(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .onFocusEvent {
                    editorFocused = it.isFocused
                }
                .bringIntoViewRequester(bringIntoViewRequester),
            colors = RichTextEditorDefaults.richTextEditorColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text("Write something...",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Gray
                    ))
            },
            onTextLayout = {textLayoutResult = it},
        )
        Spacer(Modifier.height(30.dp))
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .imePadding()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    IconButton(
                        onClick = {
                            state.toggleParagraphStyle(paragraphStyle = ParagraphStyle(TextAlign.Left))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.AlignLeft,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleParagraphStyle(paragraphStyle = ParagraphStyle(TextAlign.Center))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.AlignCenter,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleParagraphStyle(paragraphStyle = ParagraphStyle(TextAlign.Right))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.AlignRight,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleParagraphStyle(paragraphStyle = ParagraphStyle(TextAlign.Justify))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.AlignJustify,"")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp)
                        .width(2.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)))
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleSpanStyle(spanStyle = SpanStyle(fontWeight = FontWeight.Bold))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.Bold,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleSpanStyle(spanStyle = SpanStyle(fontStyle = FontStyle.Italic))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.Italic,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleSpanStyle(spanStyle = SpanStyle(textDecoration = TextDecoration.Underline))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.Underline,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleSpanStyle(spanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough))
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.Strikethrough,"")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp)
                        .width(2.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)))
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleOrderedList()
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.ListOrdered,"")
                    }
                }
                item {
                    IconButton(
                        onClick = {
                            state.toggleUnorderedList()
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Lucide.List,"")
                    }
                }

            }
        }
    }
}