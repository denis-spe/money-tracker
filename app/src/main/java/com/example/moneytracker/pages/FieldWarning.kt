package com.example.moneytracker.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class FieldWarning(
    var message: String? = null,
    var icon: ImageVector? = Icons.Default.Warning
)
