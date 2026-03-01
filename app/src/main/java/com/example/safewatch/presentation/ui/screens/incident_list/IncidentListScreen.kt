package com.example.safewatch.presentation.ui.screens.incident_list

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safewatch.domain.model.Incident
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun IncidentListScreen() {

    val context = LocalContext.current
    val app = context.applicationContext as Application

    val vm: IncidentListViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    )

    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SafeWatch") },
                actions = {
                    TextButton(onClick = { vm.refresh() }) { Text("Refresh") }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val s = uiState) {
                is IncidentListUiState.Loading -> CenterText("Loading…")
                is IncidentListUiState.Error -> CenterText("Error: ${s.message}")
                is IncidentListUiState.Empty -> CenterText("No incidents")
                is IncidentListUiState.Success -> {
                    val listState = rememberLazyListState()
                    val pullState = rememberPullRefreshState(
                        refreshing = s.isRefreshing,
                        onRefresh = { vm.refresh() }
                    )

                    val shouldLoadMore by remember {
                        derivedStateOf {
                            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                            val total = listState.layoutInfo.totalItemsCount
                            total > 0 && lastVisible >= total - 3
                        }
                    }

                    LaunchedEffect(shouldLoadMore) {
                        if (shouldLoadMore) vm.loadMore()
                    }

                    Box(Modifier.fillMaxSize().pullRefresh(pullState)) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(s.items) { item -> IncidentCard(item) }

                            item {
                                when {
                                    s.isLoadingMore -> CenterRowLoading()
                                    s.endReached -> CenterRowText("No more")
                                    s.errorMessage != null -> CenterRowText("Load more error: ${s.errorMessage}")
                                }
                            }
                        }

                        PullRefreshIndicator(
                            refreshing = s.isRefreshing,
                            state = pullState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IncidentList(items: List<Incident>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            IncidentCard(item)
        }
    }
}

@Composable
private fun IncidentCard(item: Incident) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.cameraName, style = MaterialTheme.typography.titleMedium)
            if (!item.locationLabel.isNullOrBlank()) {
                Text(item.locationLabel!!, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                formatTime(item.happenedAtEpochMillis),
                style = MaterialTheme.typography.bodySmall
            )
            if (!item.licensePlate.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Plate: ${item.licensePlate}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun CenterText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text)
    }
}

@Composable
private fun CenterRowLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CenterRowText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text)
    }
}

private fun formatTime(epochMillis: Long): String {
    val df = SimpleDateFormat("MMM d, HH:mm", Locale.US)
    return df.format(Date(epochMillis))
}