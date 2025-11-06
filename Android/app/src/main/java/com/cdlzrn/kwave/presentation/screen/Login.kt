package com.cdlzrn.kwave.presentation.screen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.ServiceRegisterButton
import com.cdlzrn.kwave.presentation.asset.TextField
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.utils.outputTransformation.passwordOutputTransformation
import com.cdlzrn.kwave.presentation.theme.ui.Error
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHand
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.utils.inputTransformation.passwordInputTransformation
import com.cdlzrn.kwave.presentation.viewmodel.AuthEvent
import com.cdlzrn.kwave.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.receiveAsFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
    navigateToRegister: () -> Unit,
    onClickForgotPassword: () -> Unit,
    viewModel: AuthViewModel = viewModel<AuthViewModel>()
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.receiveAsFlow().collect { event ->
            when (event) {
                is AuthEvent.LaunchAuthIntent -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.uri)
                    context.startActivity(intent)
                }

                AuthEvent.NavigateToMain -> {
                    navigateToMain()
                }

                AuthEvent.NavigateToInterest -> {}
            }
        }
    }

    LaunchedEffect(viewModel.login.text, viewModel.password.text) {
        if (viewModel.errorVisible) viewModel.changeErrorVisible(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit){
                detectTapGestures {
                    focusManager.clearFocus()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .paint(
                    painter = painterResource(id = R.drawable.gradient_login),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 98.dp),
                text = "K-wave",
                fontFamily = GochiHand,
                fontWeight = FontWeight.Light,
                fontSize = 64.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.padding(top = 120.dp),
                text = "Вход",
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequester = emailFocus),
                    placeHolder = "E-mail/Номер телефона/никнейм",
                    isError = viewModel.errorVisible,
                    state = viewModel.login
                )

                TextField(
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .focusRequester(focusRequester = passwordFocus),
                    placeHolder = "Пароль",
                    inputTransformation = passwordInputTransformation(),
                    outputTransformation = passwordOutputTransformation(),
                    isError = viewModel.errorVisible,
                    state = viewModel.password
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        this@Row.AnimatedVisibility(
                            visible = viewModel.errorVisible
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.warning),
                                    contentDescription = "Warning",
                                    tint = Error
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 2.dp, top = 1.dp),
                                    text = "Неправильный логин или пароль",
                                    style = TextStyle(
                                        color = Error,
                                        fontSize = 12.sp,
                                        fontFamily = Gilroy,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier
                            .clickable { onClickForgotPassword() }
                            .align(Alignment.CenterVertically),

                        text = "Забыли пароль?",
                        style = getGradientTextStyle()
                    )
                }

                GradientButton(
                    modifier = Modifier
                        .padding(top = 106.dp),
                    fill = false,
                    text = "Войти",
                    onClick = viewModel::onLoginClick
                )

                Text(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    style = getGradientTextStyle(),
                    text = "или войти с помощью",
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 13.dp),
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

                Row(
                    modifier = Modifier
                        .padding(top = 23.dp)
                ){
                    Text(
                        text = "Нет аккаунта? ",
                        color = Gray100,
                        fontSize = 14.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        modifier = Modifier
                            .clickable{
                                navigateToRegister()
                            },
                        style = getGradientTextStyle(),
                        text = "Создать",
                        fontSize = 14.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewLogin() {
//    Login(
//        navigateToMain = {},
//        navigateToRegister = {},
//        onClickForgotPassword = {},
//    )
//}