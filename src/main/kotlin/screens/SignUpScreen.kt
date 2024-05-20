package screens

import Database.DatabaseOperations
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.MyTextFilledComponent
import components.PasswordTextFilledComponent
import components.TextComponent
import navigation.FitnessCentreAppRouter
import navigation.Screen

fun ind(){

}

@Composable
fun SignUpScreen() {
    val phoneNumberState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val repeatedPasswordState = remember { mutableStateOf("") }
    val errorTextEmail = remember { mutableStateOf("") }
    val errorTextPassword = remember { mutableStateOf("") }
    val errorTextRepeatedPassword = remember { mutableStateOf("") }
    val errorTextPhoneNumber = remember { mutableStateOf("") }
    val errorTextAccount = remember { mutableStateOf("") }
    val errorText = remember { mutableStateOf("") }

    fun validateInputs(): String {
        return when {
            !InputValidator.isValidPhoneNumber(phoneNumberState.value) -> "Invalid telephone format"
            !InputValidator.isValidEmail(emailState.value) -> "Invalid email format"
            !InputValidator.isValidPassword(passwordState.value) -> "Password must be at\n least 8 characters"
            !InputValidator.doPasswordsMatch(passwordState.value, repeatedPasswordState.value) -> "Passwords do not match"
            else -> ""
        }
    }

//    var navigateToSignUp = when {
//        !InputValidator.isValidPhoneNumber(phoneNumberState.value) -> false
//        !InputValidator.isValidEmail(emailState.value) -> false
//        !InputValidator.isValidPassword(passwordState.value) -> false
//        !InputValidator.isValidPassword(repeatedPasswordState.value) -> false
//        !InputValidator.doPasswordsMatch(passwordState.value, repeatedPasswordState.value) -> false
//        else -> true
//    }
//


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
                MyTextFilledComponent(
                    labelValue = "Phone Number",
                    resourceId = "images/telephone.png",
                    onValueChanged = { newValue ->
                        phoneNumberState.value = newValue

                    },
                    errorText = if (errorText.value == "Invalid telephone format") errorText.value else ""
                )
                MyTextFilledComponent(
                        labelValue = "Email",
                resourceId = "images/email.png",
                onValueChanged = { newValue ->
                    emailState.value = newValue
                },
                    errorText = if (errorText.value == "Invalid email format") errorText.value else ""
                )
                PasswordTextFilledComponent(
                    labelValue = "Password",
                    resourceId = "images/lock.png",
                    onValueChanged = { newValue ->
                        passwordState.value = newValue
                    },
                    errorText = if (errorText.value == "Password must be at\n least 8 characters") errorText.value else ""
                )
                PasswordTextFilledComponent(
                    labelValue = "Repeated Password",
                    resourceId = "images/lock.png",
                    onValueChanged = { newValue ->
                        repeatedPasswordState.value = newValue
                    },
                    errorText = if (errorText.value == "Passwords do not match") errorText.value else ""
                )

                ButtonComponent("Sign Up", onClick = {
                    errorText.value = validateInputs()
                    if (errorText.value.isEmpty()) {
                        val account = DatabaseOperations.getClientIdByPhoneNumber(phoneNumberState.value)
                        if (account != null) {
                            val hashedPassword = DatabaseOperations.hashPassword(passwordState.value)
                            DatabaseOperations.addClientAccount(emailState.value, hashedPassword!!, account)
                            UserViewModel.userLogin = emailState.value // Сохраните логин пользователя
                            FitnessCentreAppRouter.navigateTo(Screen.ProfileScreen)
                        } else {
                            errorTextAccount.value = "There is no client\nwith this number"
                        }
                    }
                },  errorText =  errorTextAccount.value )
                ButtonComponent("Login"){ FitnessCentreAppRouter.navigateTo(Screen.LoginScreen)}

            }

        }
    }




@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){
   SignUpScreen()
}