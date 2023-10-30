@file:OptIn(ExperimentalComposeUiApi::class)

package me.yihtseu.jishi.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.res.stringResource
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.theme.HorizontalCardPadding
import me.yihtseu.jishi.ui.theme.VerticalCardPadding
import me.yihtseu.jishi.ui.theme.shapes
import me.yihtseu.jishi.ui.theme.typography

@Composable
fun LoginBox(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit
) {
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

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
                    Text(text = stringResource(R.string.account), style = typography.labelMedium)
                },
                singleLine = true,
                shape = shapes.large
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth().autofill(
                    autofillTypes = listOf(AutofillType.Password),
                    onFill = { password.value = it }
                ),
                label = {
                    Text(text = stringResource(R.string.passwd), style = typography.labelMedium)
                },
                singleLine = true,
                shape = shapes.large
            )
            Button(
                modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
                onClick = { onLogin(username.value, password.value) },
                shape = shapes.large
            ) {
                Text(text = stringResource(R.string.confirm), style = typography.labelMedium)
            }
        }
    }
}