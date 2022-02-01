package view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import view.model.Action
import view.model.ActionType
import view.theme.titanWhite
import view.util.AppConfig
import view.util.ValidationType
import view.util.ValidationUtil

@Composable
fun AddSeparatorWindow(
    isWindowOpened:Boolean,
    onCloseClicked:()-> Unit,
    onSepCreated:(ac:Action) -> Unit
) {
    
    var optionSepName by remember { mutableStateOf("") }
    var optionSepNameErrorStatus by remember { mutableStateOf(false) }
    var optionLinesBf by remember { mutableStateOf("") }
    var optionLinesBfErrorStatus by remember { mutableStateOf(true) }
    var optionLinesAf by remember { mutableStateOf("") }
    var optionLinesAfErrorStatus by remember { mutableStateOf(true) }
    var optionSepRowLength by remember { mutableStateOf("") }
    var optionSepRowLengthErrorStatus by remember { mutableStateOf(true) }
    val icon = painterResource("logo.png")

    if (isWindowOpened) {
        Window(
            onCloseRequest = { onCloseClicked() },
            state = WindowState(size = DpSize(400.dp, 700.dp)),
            title = "Add separator",
            icon = icon,
            resizable = false,
            alwaysOnTop = false
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(titanWhite),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ADD SEPARATOR",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(30.dp))
                    OptionBox(
                        optionTitleName = "Separator name",
                        value = optionSepName,
                        isNumber = false,
                        errorMessage = "",
                        optionSepNameErrorStatus
                    ) {
                        optionSepName = it
                    }
                    OptionBox(
                        optionTitleName = "Lines before",
                        value = optionLinesBf,
                        isNumber = false,
                        errorMessage = "Must be a positive number.",
                        optionLinesBfErrorStatus
                    ) {
                        optionLinesBf = it
                        optionLinesBfErrorStatus = ValidationUtil.validateNumber(optionLinesBf)
                    }
                    OptionBox(
                        optionTitleName = "Lines after",
                        value = optionLinesAf,
                        isNumber = false,
                        errorMessage = "Must be a positive number.",
                        optionLinesAfErrorStatus
                    ) {
                        optionLinesAf = it
                        optionLinesAfErrorStatus = ValidationUtil.validateNumber(optionLinesAf)
                    }
                    OptionBox(
                        optionTitleName = "Row length",
                        value = optionSepRowLength,
                        isNumber = false,
                        errorMessage = "Must be a positive number.",
                        optionSepRowLengthErrorStatus
                    ) {
                        optionSepRowLength = it
                        optionSepRowLengthErrorStatus = ValidationUtil.validateNumber(optionSepRowLength)
                    }

                }

                Column(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Btn("ADD", BtnType.BTN_ACTION_FINAL, Icons.Filled.Save,true) {
                        val someErrors = optionLinesAfErrorStatus || optionLinesBfErrorStatus || optionSepNameErrorStatus
                        if(!someErrors) {
                            val ac = Action(ActionType.ACTION_ADD_SEP).apply {
                                separatorTitle = optionSepName
                                separatorLinesAf = optionLinesAf.toInt()
                                separatorLinesBf = optionLinesBf.toInt()
                                separatorRowLength = optionSepRowLength.toInt()
                            }
                            onSepCreated(ac)
                            onCloseClicked()
                        }

                    }
                    Spacer(Modifier.height(10.dp))
                }

            }

        }
    }

}