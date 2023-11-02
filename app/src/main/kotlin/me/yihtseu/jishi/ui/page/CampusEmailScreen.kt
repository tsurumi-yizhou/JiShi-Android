@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
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
import me.yihtseu.jishi.ui.component.box.LoadingBox
import me.yihtseu.jishi.ui.theme.*
import me.yihtseu.jishi.vm.CampusEmailViewModel

@Composable
fun CampusEmailScreen(
    controller: NavHostController,
    viewModel: CampusEmailViewModel = hiltViewModel()
) {
    val host = remember { SnackbarHostState() }
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.campus_account))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(host)
        }
    ) { paddingValues ->
        if (state is State.Success) (state as State.Success).let {
            Column(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
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
        } else {
            LoadingBox(modifier = Modifier.padding(paddingValues).fillMaxSize())
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.init()
    }
}