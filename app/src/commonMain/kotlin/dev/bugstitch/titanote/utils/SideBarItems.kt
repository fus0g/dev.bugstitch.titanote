package dev.bugstitch.titanote.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.Github
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Notebook
import com.composables.icons.lucide.Shield
import com.composables.icons.lucide.Store
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.github
import titanote.app.generated.resources.privacyPolicy
import titanote.app.generated.resources.store
import titanote.app.generated.resources.tos

data class SideBarItem(
    val name: StringResource,
    val logo: ImageVector,
    val url: String
)

val sideBarItems = listOf(
    SideBarItem(
        logo = Lucide.Store,
        name = Res.string.store,
        url = "https://play.google.com/store/apps/details?id=dev.bugstitch.titanote"
    ),
    SideBarItem(
        logo = Lucide.Github,
        name = Res.string.github,
        url = "https://github.com/fus0g/dev.bugstitch.titanote"
    ),
    SideBarItem(
        logo = Lucide.Shield,
        name = Res.string.privacyPolicy,
        url = "https://github.com/fus0g/dev.bugstitch.titanote/blob/master/PrivacyPolicy.MD"
    ),
    SideBarItem(
        logo = Lucide.Notebook,
        name = Res.string.tos,
        url = "https://github.com/fus0g/dev.bugstitch.titanote/blob/master/ToC.MD"
    )
)
