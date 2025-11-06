package com.cdlzrn.kwave.presentation.screen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.Graph.saveUserId
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.asset.CheckBox
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.ServiceRegisterButton
import com.cdlzrn.kwave.presentation.asset.TextField
import com.cdlzrn.kwave.presentation.theme.ui.Error
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gradient
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray55
import com.cdlzrn.kwave.presentation.utils.inputTransformation.emailInputTransformation
import com.cdlzrn.kwave.presentation.utils.inputTransformation.nameInputTransformation
import com.cdlzrn.kwave.presentation.utils.inputTransformation.passwordInputTransformation
import com.cdlzrn.kwave.presentation.utils.inputTransformation.phoneInputTransformation
import com.cdlzrn.kwave.presentation.utils.outputTransformation.passwordOutputTransformation
import com.cdlzrn.kwave.presentation.utils.validator.DateValidator
import com.cdlzrn.kwave.presentation.viewmodel.AuthEvent
import com.cdlzrn.kwave.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Register(
    modifier: Modifier = Modifier,
    navigateToInterests: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel<AuthViewModel>()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState(
        selectableDates = DateValidator
    )
    var showDatePicker by remember { mutableStateOf(false) }

    var isErrorNecessarily by remember { mutableStateOf(false) }
    var isErrorMail by remember { mutableStateOf(false) }
    var isErrorPhone by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.receiveAsFlow().collect { event ->
            when (event) {
                is AuthEvent.LaunchAuthIntent -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.uri)
                    context.startActivity(intent)
                }

                AuthEvent.NavigateToMain -> { }

                AuthEvent.NavigateToInterest -> {
                    navigateToInterests()
                }
            }
        }
    }

    LaunchedEffect(
        viewModel.name.text,
        viewModel.date,
        viewModel.phone.text,
        viewModel.registerPassword.text
    ) {
        if (isErrorNecessarily){
            isErrorNecessarily = false
            errorMessage = ""
        }
    }

    LaunchedEffect( viewModel.isAgreeUserAgreement ) {
        if (viewModel.isAgreeUserAgreement)
            errorMessage = ""
    }

    LaunchedEffect(viewModel.phone.text) {
        if (isErrorPhone){
            isErrorPhone = false
            errorMessage = ""
        }
    }

    LaunchedEffect(viewModel.mail.text) {
        if (isErrorMail){
            isErrorMail = false
            errorMessage = ""
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo"
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = "Регистрация",
                color = Gradient,
                fontSize = 32.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 25.dp)
            )

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                placeHolder = "Имя",
                state = viewModel.name,
                isNecessarily = true,
                inputTransformation = nameInputTransformation(),
                isError = isErrorNecessarily,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                )
            )

            Box(
                modifier = modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(30))
                    .border(
                        color = if (isErrorNecessarily) Error else Gray55,
                        width = if (isErrorNecessarily) 3.dp else 1.dp,
                        shape = RoundedCornerShape(30)
                    )
                    .background(Color.White)
                    .clickable { showDatePicker = true }
                    .padding(16.dp)
                ,
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    text = viewModel.date,
                    fontSize = 14.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.SemiBold,
                    color = if (viewModel.date == "Дата рождения*") Gray55 else Gray100
                )
            }

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                placeHolder = "E-mail",
                state = viewModel.mail,
                isError = isErrorMail,
                inputTransformation = emailInputTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None,
                )
            )

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                placeHolder = "Номер телефона",
                state = viewModel.phone,
                isNecessarily = true,
                isError = isErrorNecessarily || isErrorPhone,
                inputTransformation = phoneInputTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone,
                    capitalization = KeyboardCapitalization.None,
                )
            )

            TextField(
                modifier = Modifier.padding(bottom = 20.dp),
                placeHolder = "Пароль",
                state = viewModel.registerPassword,
                inputTransformation = passwordInputTransformation(),
                outputTransformation = passwordOutputTransformation(),
                isNecessarily = true,
                isError = isErrorNecessarily,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None,
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckBox(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 8.dp),
                    checked = viewModel.isAgreeUserAgreement,
                    onCheckedChange = {
                        viewModel.changeAgreeUserAgreement(!viewModel.isAgreeUserAgreement)
                    },
                )

                Text(
                    text = buildAnnotatedString { userAgreement(viewModel) }
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 28.dp),
                text = errorMessage,
                color = Error,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )

            GradientButton(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                fill = true,
                text = "Создать аккаунт",
                onClick = {
                    if (viewModel.name.text.trim() == ""
                        || viewModel.date == ""
                        || viewModel.phone.text.trim() == ""
                        || viewModel.registerPassword.text.trim() == ""
                    ){
                        isErrorNecessarily = true
                        errorMessage = "Заполните все обязательные поля!"
                        return@GradientButton
                    }

                    if (viewModel.phone.text.length != 12){
                        isErrorPhone = true
                        errorMessage = "Некорректный номер телефона"
                        return@GradientButton
                    }

                    if (!viewModel.mail.text.matches(
                            Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"))){
                        isErrorMail = true
                        errorMessage = "Некорректный адресс электронной почты"
                        return@GradientButton
                    }

                    if (!viewModel.isAgreeUserAgreement){
                        errorMessage = "Нужно принять пользовательское соглашение"
                        return@GradientButton
                    }

                    scope.launch {
                        if (viewModel.isExistUserMail()) {
                            isErrorMail = true
                            errorMessage = "Пользователь с такой почтой уже существует"
                            return@launch
                        }
                        if (viewModel.isExistUserPhone()) {
                            isErrorPhone = true
                            errorMessage = "Пользователь с таким номером телефона уже существует"
                            return@launch
                        }

                        val userId = viewModel.createUser()

                        saveUserId(userId)
                        navigateToInterests()
                    }
                }
            )
            Text(
                modifier = Modifier.padding(bottom = 13.dp),
                text = "или создать аккаунт с помощью"
            )
            Row(
                modifier = Modifier.padding(bottom = 25.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ServiceRegisterButton(
                    painter = painterResource(R.drawable.google_logo),
                    contentDescription = "Auth by Google",
                    onClick = viewModel::onGoogleClick
                )
                Spacer(modifier = Modifier.width(36.dp))
                ServiceRegisterButton(
                    painter = painterResource(R.drawable.apple),
                    contentDescription = "Auth by Apple",
                    imageModifier = Modifier.offset(y = (-2).dp),
                    onClick = viewModel::onAppleClick
                )
            }

            Text(
                text = buildAnnotatedString {
                    loginAnnotatedString(
                        onClick = { navigateToLogin() }
                    )
                }
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            viewModel.onDateSelected(it)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun AnnotatedString.Builder.userAgreement(viewmodel: AuthViewModel) {
    val style = SpanStyle(
        fontSize = 12.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Light,
        color = Color.Black
    )

    withStyle(
        style = style
    ) {
        append("Я принимаю условия ")

        withLink(
            LinkAnnotation.Url(
                url = viewmodel.getUserAgreementUrl(),
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = Gradient
                    )
                )
            )
        ) {
            append("Пользовательского соглашения")
        }

        append("\nи даю своё согласие на обработку персональных данных.")
    }
}

private fun AnnotatedString.Builder.loginAnnotatedString(
    onClick: () -> Unit
) {
    val style = SpanStyle(
        fontSize = 12.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Light,
        color = Color.Black
    )

    withStyle(
        style = style
    ) {
        append("Уже есть аккаунт? ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "login",
                linkInteractionListener = LinkInteractionListener {
                    onClick()
                },
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = Gradient
                    )
                )
            )
        ) {
            append("Войти")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRegister() {
    Register(
        navigateToInterests = {},
        navigateToLogin = {}
    )
}