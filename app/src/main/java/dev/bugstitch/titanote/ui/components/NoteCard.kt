package dev.bugstitch.titanote.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Trash
import dev.bugstitch.titanote.R
import dev.bugstitch.titanote.ui.theme.ZenColors
import dev.bugstitch.titanote.utils.LogoString
import dev.bugstitch.titanote.utils.Logos
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteCard(color:Int,
             logo:Int,
             title:String,
             content:String,
             date:Date,
             delete:() -> Unit,
             edit:() -> Unit,
             onClick:() -> Unit
) {
    Box(modifier = Modifier.padding(4.dp)) {
        Column(modifier = Modifier.width(175.dp)
            .height(220.dp)
            .background(color = ZenColors.NoteColors.colorList[color], shape = RoundedCornerShape(15.dp))
            .padding(8.dp)
            .clickable { onClick() },
            verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = SimpleDateFormat("dd/MM/yyyy 'at' hh:mm a", Locale.getDefault()).format(date).toString(),
                    fontSize = 10.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    LogoButton(icon = Logos[logo], contentDescription = LogoString[logo],static = true, size = 35.dp)
                    Text(modifier = Modifier.padding(start = 8.dp),text = title, maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = ZenColors.Night,
                        fontSize = 16.sp
                    )
                }
                Text(text = content, maxLines = 5,
                    color = ZenColors.Night,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = delete) {
                    Icon(Lucide.Trash, contentDescription = stringResource(R.string.delete),
                        modifier = Modifier.size(18.dp),
                        tint = ZenColors.Night)
                }
                IconButton(onClick = edit) {
                    Icon(Lucide.Pen, contentDescription = stringResource(R.string.edit),
                        modifier = Modifier.size(18.dp),
                        tint = ZenColors.Night)
                }
            }
        }
    }

}