package view.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import view.model.Action

@Composable
fun ActionItem(action:Action, onDeleteClick:() -> Unit) {

    Card (
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(0.4.dp, Color.LightGray)
    ) {
        Row (
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.width(5.dp))
                Icon(action.getIcon(), null)
                Spacer(Modifier.width(10.dp))
                Text(action.getTitle().toString(), style = MaterialTheme.typography.body1)
                Spacer(Modifier.width(20.dp))
                Text(action.getSubtitle().toString(), color = Color.LightGray, style = MaterialTheme.typography.body1, maxLines = 10)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BtnDelete(onDeleteClick)
                Spacer(Modifier.width(5.dp))
            }
        }
    }

}

