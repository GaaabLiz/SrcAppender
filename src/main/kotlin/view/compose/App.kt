package view.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Segment
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import view.model.Action
import view.model.ActionType
import view.theme.titanWhite
import view.theme.tropicalBlue
import view.util.FileUtil



@Composable
@Preview
fun App() {
    val btnAddFileEnabledStatus by remember { mutableStateOf(true) }
    val btnAddSepEnabledStatus by remember { mutableStateOf(true) }
    var exportBannerVisible by remember { mutableStateOf(false) }
    var errorDialogVisible by remember { mutableStateOf(false) }
    var errorDialogText by remember { mutableStateOf("") }
    var successDialogVisible by remember { mutableStateOf(false) }
    var successDialogText by remember { mutableStateOf("") }

    val actionList = remember {
        mutableStateListOf<Action>()
    }
    var isSepWindowOpen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(titanWhite)) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(70.dp),
            color = tropicalBlue,
            elevation = 5.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Btn(
                        text = "Add file",
                        type = BtnType.BTN_ACTION,
                        imageVectorIcon = Icons.Filled.AddCircleOutline,
                        btnAddFileEnabledStatus
                    ) {
                        val file = FileUtil.openDialogAndGetDir()
                        if(file != null) {
                            val ac = Action(ActionType.ACTION_ADD_FILE).apply {
                                fileName = file.name
                                filePath = file
                            }
                            actionList.add(ac)
                        }
                    }
                    Spacer(Modifier.width(20.dp))
                    Btn(
                        text = "Add separator",
                        type = BtnType.BTN_ACTION,
                        imageVectorIcon = Icons.Filled.Segment,
                        btnAddSepEnabledStatus
                    ) {
                        isSepWindowOpen = true
                    }
                    AddSeparatorWindow(isSepWindowOpen, {isSepWindowOpen = false}) {
                        actionList.add(it)
                    }
                }
                Row {
                    Btn(
                        text = "Create output file",
                        type = BtnType.BTN_ACTION_FINAL,
                        imageVectorIcon = Icons.Filled.PostAdd,
                        btnAddSepEnabledStatus
                    ) {
                        CoroutineScope(IO).launch {
                            if(actionList.isNotEmpty()) {
                                errorDialogVisible = false
                                FileUtil.createOutputFile(
                                    acList = actionList.toList(),
                                    showBanner = { exportBannerVisible = true },
                                    hideBanner = { exportBannerVisible = false },
                                )
                            } else {
                                errorDialogText = "File list is empty."
                                errorDialogVisible = true
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 20.dp, end = 20.dp),
        ) {
            if(errorDialogVisible) DialogCard(DialogCardType.ERROR, errorDialogText)
            if(successDialogVisible) DialogCard(DialogCardType.SUCCESS, successDialogText)
            if(exportBannerVisible) ExportInfoCard()
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(items = actionList) { ac ->
                ActionItem(ac) { actionList.remove(ac) }
            }
        }

    }

}
