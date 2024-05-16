package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.ButtonComponent
import components.MyTextFilledComponent
import components.PasswordTextFilledComponent
import components.TextComponent
import kotlinx.coroutines.delay

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        color = Color.Red,
        fontSize = 14.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun CustomToast(message: String, durationMillis: Long = 2000) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        visible = true
        delay(durationMillis)
        visible = false
    }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(androidx.compose.ui.Alignment.TopCenter)
                .padding(top = 16.dp),
            contentAlignment = androidx.compose.ui.Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(message, color = Color.White)
            }
        }
    }
}
@Composable
fun SignUpScreen(onNavigate: () -> Unit) {
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorMessages = remember { mutableStateListOf<String>() }

    fun navigateToSignUp() {
        errorMessages.clear()
        when {
            !InputValidator.isValidName(firstNameState.value) -> errorMessages.add("Некорректное имя")
            !InputValidator.isValidName(lastNameState.value) -> errorMessages.add("Некорректная фамилия")
            !InputValidator.isValidEmail(emailState.value) -> errorMessages.add("Некорректный Email")
            !InputValidator.isValidPassword(passwordState.value) -> errorMessages.add("Некорректный пароль")
            errorMessages.isEmpty() -> onNavigate()
        }
//        errorMessages.forEach { message ->
//            CustomToast(message = message)
//        }
    }

    fun navigateToSignIn() {
        onNavigate()
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TextComponent(value = "Hey there,", 24, FontWeight.Normal, 40)
                TextComponent(value = "Create an Account", 30, FontWeight.Bold, 0)
                Spacer(modifier = Modifier.height(15.dp))
                MyTextFilledComponent(labelValue = "First Name", resourceId = "images/user.png") {
                        newValue -> firstNameState.value = newValue
                }
                MyTextFilledComponent(labelValue = "Last Name", resourceId = "images/user.png"){
                    newValue -> lastNameState.value = newValue
                }
                MyTextFilledComponent(labelValue = "Email", resourceId = "images/email.png"){
                    newValue -> emailState.value = newValue
                }
                PasswordTextFilledComponent(labelValue = "Password", resourceId = "images/lock.png"){
                    newValue -> passwordState.value = newValue
                }
                ButtonComponent("Sign Up"){navigateToSignUp()}
                ButtonComponent("Login"){navigateToSignIn()}

            }

    }

}


@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){
   SignUpScreen({})
}