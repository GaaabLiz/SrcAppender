package view.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import view.theme.dodgerBlue
import view.theme.royalBlue
import view.theme.titanWhite
import view.util.ValidationType
import view.util.ValidationUtil

@Composable
fun OptionBox(
    optionTitleName:String,
    value:String,
    isNumber:Boolean = false,
    errorMessage:String,
    errorStatus:Boolean,
    onValueChange:(String) -> Unit
) {


    Column(Modifier.fillMaxWidth()) {
        Text(
            text = optionTitleName,
            style = MaterialTheme.typography.body1
        )
        Spacer( modifier = Modifier.height(3.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = true,
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            trailingIcon = {
                if (errorStatus)
                    Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
            },
            isError = errorStatus,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = dodgerBlue,
                backgroundColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = if(isNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else  KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        if (errorStatus) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}


@Composable
fun ExportInfoCard(exportPath:String, currentOperationText:String) {
    Card(
        modifier = Modifier.fillMaxWidth().height(75.dp),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.5.dp, royalBlue),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(15.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = royalBlue,
                    strokeWidth = 5.dp
                )
            }
            Spacer(Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(2F).fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Creating output file on: '$exportPath'", style = MaterialTheme.typography.body2)
                Spacer(Modifier.height(4.dp))
                Text(currentOperationText, style = MaterialTheme.typography.body2, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun DialogCard(
    cardType: DialogCardType,
    text : String
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(40.dp),
        shape = RoundedCornerShape(5.dp),
        border = if(cardType == DialogCardType.ERROR) BorderStroke(1.5.dp, Color.Red) else BorderStroke(1.5.dp, Color.Green),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if(cardType == DialogCardType.ERROR)Icons.Filled.Error else Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = if(cardType == DialogCardType.ERROR) Color.Red else Color.Green
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                color = if(cardType == DialogCardType.ERROR) Color.Red else Color.Green
            )
        }
    }
}

enum class DialogCardType {ERROR, SUCCESS}
enum class DialogCardViewOption {SHOW, HIDE}