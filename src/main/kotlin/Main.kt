
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.github.aakira.napier.Napier
import view.compose.App
import view.util.AppConfig

fun main() = application {
    val icon = painterResource("logo.png")
    Tray(
        icon = icon,
        menu = {
            Item("Quit view.compose.App", onClick = ::exitApplication)
        }
    )

    Window(
        state = WindowState(size = DpSize(1600.dp, 900.dp)),
        onCloseRequest = ::exitApplication,
        title = AppConfig.APP_NAME,
        icon = icon
    ) {
        Napier.i("Application start...")
        App()
    }


}
