import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.LoginScreen
import screens.Screen
import screens.SignUpScreen

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

    when (currentScreen) {
        Screen.LOGIN -> LoginScreen { currentScreen = Screen.SIGN_UP }
        Screen.SIGN_UP -> SignUpScreen { currentScreen = Screen.LOGIN }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
