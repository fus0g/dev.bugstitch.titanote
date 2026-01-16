package dev.bugstitch.titanote.presentation.theme

import androidx.compose.ui.graphics.Color
import titanote.app.generated.resources.PastelAqua
import titanote.app.generated.resources.PastelBlue
import titanote.app.generated.resources.PastelGreen
import titanote.app.generated.resources.PastelLavender
import titanote.app.generated.resources.PastelLilac
import titanote.app.generated.resources.PastelMint
import titanote.app.generated.resources.PastelOrange
import titanote.app.generated.resources.PastelPeach
import titanote.app.generated.resources.PastelPink
import titanote.app.generated.resources.PastelYellow
import titanote.app.generated.resources.Res

object ZenColors {
    // White Theme
    val WhiteSmoke = Color(0xFFF5F5F5) // background #F5F5F5
    val AntiFlashWhite = Color(0xFFEEEEEE) // onBackground #EEEEEE

    // Dark Theme
    val Night = Color(0xFF121212) // background #121212
    val EerieBlack = Color(0xFF171717) // onBackground #171717

    // App Accent
    val SeaFoamGreen = Color(0xFF2ED3B8) // #2ED3B8

    // Notes Colors
    object NoteColors {
        val PastelBlue = Color(0xFFAEC6CF)      // Soft pastel blue
        val PastelYellow = Color(0xFFFFE5A5)    // Soft pastel yellow
        val PastelPink = Color(0xFFFFD1DC)      // Soft pastel pink
        val PastelGreen = Color(0xFFB5EAD7)     // Light pastel mint green
        val PastelLavender = Color(0xFFE6E6FA)  // Light pastel lavender
        val PastelPeach = Color(0xFFFFDAC1)     // Light pastel peach
        val PastelMint = Color(0xFFB0EACD)      // Soft pastel mint
        val PastelLilac = Color(0xFFDBB8E3)     // Light pastel lilac
        val PastelOrange = Color(0xFFFFCCB6)    // Soft pastel orange
        val PastelAqua = Color(0xFFA2D8D8)      // Soft pastel aqua

        val colorList = listOf(
            PastelBlue,
            PastelPink,
            PastelGreen,
            PastelYellow,
            PastelLavender,
            PastelPeach,
            PastelMint,
            PastelLilac,
            PastelOrange,
            PastelAqua
        )
    }
}

val colorString = listOf(
    Res.string.PastelBlue,
    Res.string.PastelPink,
    Res.string.PastelGreen,
    Res.string.PastelYellow,
    Res.string.PastelLavender,
    Res.string.PastelPeach,
    Res.string.PastelMint,
    Res.string.PastelLilac,
    Res.string.PastelOrange,
    Res.string.PastelAqua
)