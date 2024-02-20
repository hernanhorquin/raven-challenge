package com.raven.home.presentation.view.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raven.home.R
import com.raven.home.presentation.model.Article
import com.raven.home.presentation.view.state.HomeState
import com.raven.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onArticleClicked: (Article) -> Unit
) {
    val uiState: HomeState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {

    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            HomeBottomSheet(
                modifier = Modifier.fillMaxHeight(0.9f),
                viewModel = viewModel,
                onPeriodSelected = { period ->
                    viewModel.updatePeriodSelected(period)
                    coroutineScope.launch { sheetState.hide() }
                },
                onClose = { coroutineScope.launch { sheetState.hide() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 8.dp,
                    backgroundColor = Color.White,
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = MaterialTheme.typography.h5.fontSize
                            ),
                            text = stringResource(id = R.string.home_title),
                            color = Color.Black
                        )
                    },
                    actions = {
                        IconButton(onClick = { coroutineScope.launch { sheetState.show() } }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter_24),
                                contentDescription = "filter"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.showError) {
                    ErrorDialog(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        viewModel = viewModel
                    )
                }

                if (uiState.articles.isEmpty()) {
                    Column {
                        repeat(4) {
                            AnimatedShimmer()
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                        items(
                            items = uiState.articles,
                            key = { article -> article.id }
                        ) { article ->
                            ArticleItem(article = article, onClick = { onArticleClicked(article) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(modifier: Modifier, viewModel: HomeViewModel) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                )
            )
        },
        text = { Text(stringResource(id = R.string.error_message)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                onClick = {
                    viewModel.hideErrorDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        onDismissRequest = {
            viewModel.hideErrorDialog()
        }
    )
}