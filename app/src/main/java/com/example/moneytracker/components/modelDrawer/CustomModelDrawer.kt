package com.example.moneytracker.components.modelDrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ModelDrawerContents(
    isBottomSheetOpen: MutableState<Boolean>
) {

    if (isBottomSheetOpen.value) {
        ModelDrawer(
            onClose = {
                isBottomSheetOpen.value = false
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(100))
                ) {
                    Text("Hello world")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModelDrawer(
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {

    // ModalBottomSheetState
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)

    LaunchedEffect(bottomSheetState.isVisible) {
        if (bottomSheetState.isVisible) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
            onClose()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            content()
        }
    ) {
        // Empty content to prevent the sheet from taking full screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(52))
        )
    }
}
