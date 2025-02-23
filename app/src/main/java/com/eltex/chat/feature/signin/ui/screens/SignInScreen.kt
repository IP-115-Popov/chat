package com.eltex.chat.feature.signin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.R
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.feature.signin.ui.components.ErrorSignInAlertDialog
import com.eltex.chat.feature.signin.viewmodel.SignInStatus
import com.eltex.chat.feature.signin.viewmodel.SignInViewModel
import com.eltex.chat.ui.components.PasswordTextField
import com.eltex.chat.ui.components.SimpleTextField
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun SignInScreen(
    navController: NavHostController
) {
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val state = signInViewModel.state.collectAsState()
    val signInButtonEnabled =
        remember { derivedStateOf { state.value.user.user.isNotEmpty() && state.value.user.password.isNotEmpty() } }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(79.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(62.dp),
            )
            Spacer(Modifier.size(32.dp))
            Text(
                text = stringResource(R.string.enter_login_password),
                textAlign = TextAlign.Center,
                style = CustomTheme.typographySfPro.titleMedium,
                color = CustomTheme.basicPalette.white,
            )
            Spacer(Modifier.size(32.dp))
            SimpleTextField(
                value = state.value.user.user,
                placeholder = stringResource(R.string.login),
                onValueChange = { signInViewModel.setLogin(it) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.size(38.dp))
            PasswordTextField(
                value = state.value.user.password,
                placeholder = stringResource(R.string.password),
                onValueChange = { signInViewModel.setPassword(it) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.size(218.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 22.dp, end = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.basicPalette.white,
                    contentColor = CustomTheme.basicPalette.black,
                    disabledContainerColor = CustomTheme.basicPalette.white.copy(alpha = 0.5f),
                    disabledContentColor = CustomTheme.basicPalette.black.copy(alpha = 0.5f),
                ),
                enabled = signInButtonEnabled.value,
                onClick = { signInViewModel.signIn() }) {
                Text(
                    text = stringResource(R.string.enter),
                    style = CustomTheme.typographySfPro.bodyMedium,
                    color = CustomTheme.basicPalette.black
                )
            }
        }
    }

    when (state.value.status) {
        SignInStatus.SignInSuccessful -> {
            navController.navigate(NavRoutes.Main.route)
        }

        SignInStatus.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = CustomTheme.basicPalette.blue
                )
            }
        }

        is SignInStatus.Error -> {
            (state.value.status as? SignInStatus.Error)?.let {
                ErrorSignInAlertDialog(message = it.message,
                    onDismissRequest = { signInViewModel.setStatusIdle() })
            }
        }

        SignInStatus.Idle -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun SSignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}