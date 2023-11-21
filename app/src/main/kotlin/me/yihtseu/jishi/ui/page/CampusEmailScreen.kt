@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.yihtseu.jishi.MainActivity
import me.yihtseu.jishi.R
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

    Compact(
        title = stringResource(R.string.campus_account),
        controller = controller,
        message = state.message,
        loading = state.loading
    ) {
        LazyColumn(
            modifier = Modifier.nestedScroll(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
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

                    state.avatar?.let {
                        Image(
                            BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap(),
                            null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(156.dp, 200.dp).padding(InnerCardPadding)
                        )
                    }

                    state.profile?.let {

                        Text(
                            text = it.name, style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )

                        Text(
                            text = it.number, style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )

                        Text(
                            text = it.school, style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(HorizontalTextPadding, VerticalTextPadding)
                        )
                    }

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

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}