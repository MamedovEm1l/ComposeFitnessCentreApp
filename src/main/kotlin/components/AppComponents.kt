
package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import componentShapes
import ui.theme.*

@Composable
fun TextComponent(value: String, fontSize: Int, fontWeight: FontWeight, heightIn: Int){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = heightIn.dp),
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}
@Composable
fun loadImageResource(resourceId: String) = painterResource(resourceId)

@Composable
fun MyTextFilledComponent(labelValue: String, resourceId: String, onValueChanged: (String) -> Unit){
    val imagePainter = loadImageResource(resourceId)

    val textValue = remember{
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
    OutlinedTextField(
        modifier = Modifier
            .align(Alignment.Center)
            .clip(componentShapes.small)
        ,
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(painter = imagePainter, "", modifier = Modifier.size(20.dp).padding(top = 5.dp))
        }
        )}
}

@Composable
fun PasswordTextFilledComponent(labelValue: String, resourceId: String, onValueChanged: (String) -> Unit){
    val imagePainter = loadImageResource(resourceId)

    val password = remember{
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
    OutlinedTextField(
        modifier = Modifier
            .align(Alignment.Center)
            .clip(componentShapes.small)
        ,
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(painter = imagePainter, "", modifier = Modifier.size(20.dp).padding(top = 5.dp))
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                loadImageResource("images/eye.png")
            } else {
                loadImageResource("images/hide.png")
            }

            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(
                onClick = { passwordVisible.value = !passwordVisible.value },
            ) {
                Icon(iconImage, contentDescription = description,Modifier.size(20.dp).padding(top = 5.dp))
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )}
}

@Composable
fun ButtonComponent(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(300.dp)
                .height(48.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = ButtonDefaults.elevation(0.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun UnderLinedNormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 10.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight. Normal,
            fontStyle = FontStyle.Normal
        ), color = GrayColor,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}