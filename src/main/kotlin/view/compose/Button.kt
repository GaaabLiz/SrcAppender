package view.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import view.theme.dodgerBlue
import view.theme.royalBlue

enum class BtnType {BTN_ACTION, BTN_ACTION_FINAL}

@Composable
fun Btn(
    text:String,
    type: BtnType,
    imageVectorIcon : ImageVector,
    enabled : Boolean,
    onclick : () -> Unit
) {

    Button(
        onClick = onclick,
        /*modifier = Modifier.size(width = 150.dp, height = 45.dp),*/
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = getButtonColor(type), contentColor = Color.White)
    ) {
        Icon(
            imageVectorIcon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text)
    }
}

@Composable
fun BtnDelete(onclick : () -> Unit) {

    OutlinedButton(
        onClick = onclick,
        enabled = true,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Delete")
    }
}

fun getButtonColor(type: BtnType) = when(type) {
    BtnType.BTN_ACTION -> dodgerBlue
    BtnType.BTN_ACTION_FINAL -> royalBlue
}