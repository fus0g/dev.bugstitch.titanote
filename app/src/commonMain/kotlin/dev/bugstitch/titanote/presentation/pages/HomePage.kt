package dev.bugstitch.titanote.presentation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Beer
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash
import dev.bugstitch.titanote.data.Note
import dev.bugstitch.titanote.data.database.Task
import dev.bugstitch.titanote.presentation.components.*
import dev.bugstitch.titanote.utils.TopBarState
import dev.bugstitch.titanote.utils.formatForUi
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import titanote.app.generated.resources.Res
import titanote.app.generated.resources.beerJar
import titanote.app.generated.resources.no_notes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(
    notes: List<Note>,
    tasks: List<Task>,
    searchEnabled: Boolean,
    searchText: String,
    isSideBarOpen: Boolean,
    onBack: () -> Unit,
    onNoteClick: (Note) -> Unit,
    onNoteEdit: (Note) -> Unit,
    onNoteDelete: (Note) -> Unit,
    onTaskComplete: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchToggle: () -> Unit,
    onSideBarToggle: () -> Unit,
    onSideBarClose: () -> Unit,
    onCreateNote: () -> Unit,
    onCreateTask: () -> Unit,
    onPageChange: (Int) -> Unit
) {
    BackHandler { onBack() }

    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    val fabExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pagerState.currentPage)
        fabExpanded.value = false
    }

    Scaffold(
        topBar = {
            TopBar(
                topBarState = TopBarState.Home,
                searchState = searchEnabled,
                searchValue = searchText,
                onSearchValueChange = onSearchTextChange,
                onSearchButtonPressed = onSearchToggle,
                onSideBarButtonPress = onSideBarToggle,
                onBackPressed = onBack
            )
        },
        bottomBar = {
            BottomBar(
                page = pagerState.currentPage,
                onNoteClick = {
                    scope.launch { pagerState.animateScrollToPage(0) }
                },
                onTaskClick = {
                    scope.launch { pagerState.animateScrollToPage(1) }
                },
                onCreate = {
                    fabExpanded.value = !fabExpanded.value
                }
            )
        },
        floatingActionButton = {
            FloatingFabButtons(
                visible = fabExpanded.value,
                onCreateNote = {
                    onCreateNote()
                    fabExpanded.value = false
                },
                onCreateTask = {
                    onCreateTask()
                    fabExpanded.value = false
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding)
        ) { page ->

            when (page) {

                /* ---------------- Notes ---------------- */
                0 -> {
                    AnimatedVisibility(notes.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Lucide.Beer,
                                contentDescription = stringResource(Res.string.beerJar),
                                modifier = Modifier.size(90.dp)
                            )
                            Text(
                                stringResource(Res.string.no_notes),
                                fontSize = 20.sp
                            )
                        }
                    }

                    AnimatedVisibility(notes.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            notes.forEach { note ->
                                item {
                                    NoteCard(
                                        title = note.title,
                                        content = note.content,
                                        date = note.date,
                                        color = 0,
                                        logo = 0,
                                        edit = { onNoteEdit(note) },
                                        delete = { onNoteDelete(note) }
                                    ) { onNoteClick(note) }
                                }
                            }
                        }
                    }
                }

                /* ---------------- Tasks ---------------- */
                1 -> {
                    AnimatedVisibility(tasks.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No tasks yet", fontSize = 18.sp)
                        }
                    }

                    AnimatedVisibility(tasks.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            tasks.forEach { task ->
                                item {
                                    TaskCard(
                                        task = task,
                                        onComplete = { onTaskComplete(task) },
                                        onDelete = { onTaskDelete(task) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val uriHandler = LocalUriHandler.current
    SideBar(
        isOpen = isSideBarOpen,
        onCloseClick = onSideBarClose,
        onUrlClick = { uriHandler.openUri(it) }
    )
}

@Composable
fun TaskCard(
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val alpha = if (task.isCompleted) 0.5f else 1f
    val textDecoration =
        if (task.isCompleted) TextDecoration.LineThrough
        else TextDecoration.None

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onComplete() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = textDecoration
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.createdAt.formatForUi(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Lucide.Trash,
                    contentDescription = "Delete"
                )
            }
        }
    }
}
