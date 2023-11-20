@file:OptIn(ExperimentalComposeUiApi::class)

package me.yihtseu.jishi.ui.component.box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.framework.autofill
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.shapes

@Composable
fun LoginBox(
    account: String?, passwd: String?,
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit
) {
    val username = rememberSaveable { mutableStateOf(account.orEmpty()) }
    val password = rememberSaveable { mutableStateOf(passwd.orEmpty()) }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth().autofill(
                    autofillTypes = listOf(AutofillType.Username),
                    onFill = { username.value = it }
                ),
                label = {
                    Text(text = stringResource(R.string.account), style = MaterialTheme.typography.labelMedium)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.AccountCircle, null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = shapes.extraSmall
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth().autofill(
                    autofillTypes = listOf(AutofillType.Password),
                    onFill = { password.value = it }
                ),
                label = {
                    Text(text = stringResource(R.string.passwd), style = MaterialTheme.typography.labelMedium)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Key, null)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = shapes.extraSmall
            )
            Button(
                modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
                onClick = { onLogin(username.value, password.value) },
                shape = shapes.small
            ) {
                Text(text = stringResource(R.string.confirm), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}