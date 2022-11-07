package pini.mattia.coachtimer.ui.customcomposables

import androidx.compose.runtime.Composable

class MultiFabItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val enabled: Boolean,
    val onFabClick: () -> Unit
)
