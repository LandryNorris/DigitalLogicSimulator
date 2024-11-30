package com.landry.digital.logic.simulator.desktop.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuBarScope

data class MenuActions(
    val onNew: () -> Unit,
    val onSave: () -> Unit,
    val onSaveAs: () -> Unit,
    val onOpen: () -> Unit,
    val onAbout: () -> Unit)

@Composable
fun FrameWindowScope.TopMenuBar(menuActions: MenuActions) {
    MenuBar {
        FileMenu(menuActions)
        HelpMenu(menuActions)
    }
}

@Composable
fun MenuBarScope.FileMenu(menuActions: MenuActions) {
    Menu("File", mnemonic = 'F') {
        Item("New", onClick = menuActions.onNew)
        Item("Open", onClick = menuActions.onOpen)
        Item("Save", onClick = menuActions.onSave)
        Item("Save As", onClick = menuActions.onSaveAs)
    }
}

@Composable
fun MenuBarScope.HelpMenu(menuActions: MenuActions) {
    Menu("Help", mnemonic = 'H') {
        Item("About", onClick = menuActions.onAbout)
    }
}
