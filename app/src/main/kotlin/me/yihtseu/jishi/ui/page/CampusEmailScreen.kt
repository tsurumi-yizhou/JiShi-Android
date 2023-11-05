@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.MainActivity
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.ui.framework.Compact
import me.yihtseu.jishi.ui.theme.*
import me.yihtseu.jishi.vm.CampusEmailViewModel

@Composable
fun CampusEmailScreen(
    controller: NavHostController,
    viewModel: CampusEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val message = remember { mutableStateOf<String?>(null) }

    Compact(
        title = stringResource(R.string.campus_account),
        controller = controller,
        message = message.value,
        loading = state is State.Loading
    ) {
        if (state is State.Success) (state as State.Success).let {
            item {
                ElevatedCard(
                    modifier = Modifier.padding(HorizontalCardPadding, VerticalCardPadding).fillMaxWidth(),
                    shape = shapes.small
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Image(
                            BitmapFactory.decodeByteArray(it.data.avatar, 0, it.data.avatar.size).asImageBitmap(), null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(156.dp, 200.dp).padding(InnerCardPadding)
                        )
                        Text(
                            text = it.data.profile.name, style = typography.labelLarge,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                        Text(
                            text = it.data.profile.number, style = typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                        Text(
                            text = it.data.profile.school, style = typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )

                        OutlinedButton(
                            modifier = Modifier
                                .padding(HorizontalCardPadding, VerticalCardPadding)
                                .fillMaxWidth(),
                            onClick = {
                                viewModel.exit()
                                (context as MainActivity).finish()
                            }
                        ) {
                            Text(text = stringResource(R.string.exit))
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}