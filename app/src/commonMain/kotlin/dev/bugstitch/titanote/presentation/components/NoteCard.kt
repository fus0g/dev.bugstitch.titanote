package dev.bugstitch.titanote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Trash
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import dev.bugstitch.titanote.presentation.theme.ZenColors
import dev.bugstitch.titanote.utils.formatForUi
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.delete
import titanote.app.generated.resources.edit
import kotlin.time.Instant

@Composable
fun NoteCard(
    color: Int,
    logo: Int,
    title: String,
    content: String,
    date: Instant,
    delete: () -> Unit,
    edit: () -> Unit,
    onClick: () -> Unit
) {
    val richTextState = rememberRichTextState()
    richTextState.setMarkdown(content)

    Box(modifier = Modifier.padding(4.dp)) {
        Column(modifier = Modifier.fillMaxWidth()
            .sizeIn(maxWidth = 220.dp)
            .padding(8.dp)
            .clickable { onClick() },
            verticalArrangement = Arrangement.SpaceBetween) {

            Column {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(modifier = Modifier.padding(start = 8.dp),text = title, maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = ZenColors.Night,
                        fontSize = 24.sp
                    )
                }
                RichText(state = richTextState, maxLines = 5,
                    color = ZenColors.Night,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp))
            }
            Column {

                Row(modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = date.formatForUi(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row {

                        IconButton(onClick = delete) {
                            Icon(Lucide.Trash, contentDescription = stringResource(Res.string.delete),
                                modifier = Modifier.size(18.dp),
                                tint = ZenColors.Night)
                        }
                        IconButton(onClick = edit) {
                            Icon(Lucide.Pen, contentDescription = stringResource(Res.string.edit),
                                modifier = Modifier.size(18.dp),
                                tint = ZenColors.Night)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }

}