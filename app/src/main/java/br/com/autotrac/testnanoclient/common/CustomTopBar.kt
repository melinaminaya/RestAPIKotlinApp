package br.com.autotrac.testnanoclient.common

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.R
import br.com.autotrac.testnanoclient.vm.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String?,
    onBackClick: (() -> Unit)?,
    navigateToLogs: () -> Unit,
    popUpToLogin: () -> Unit,
    isSocketOn: Boolean?,
    apiIcon:Boolean,
    content: () -> Unit,
) {

    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }
    var drawerOpen by remember { mutableStateOf(false) }

    val appViewModel = viewModel<AppViewModel>()
    val isApiOnVal by appViewModel.isApiOn.observeAsState(false)
    var isApiOn by remember { mutableStateOf(isApiOnVal) }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current
    backDispatcher?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (drawerOpen) {
                // Close the drawer if it's open
                drawerOpen = false
            }
        }
    })
    var isSocketOnRemember by remember {
        mutableStateOf(isSocketOn)
    }
    LaunchedEffect(Unit) {
        appViewModel.startCheckingApiStatus()
    }
    LaunchedEffect(isSocketOn) {
        isSocketOnRemember = isSocketOn
    }
    LaunchedEffect(isApiOnVal) {
        isApiOn = isApiOnVal
    }
    TopAppBar(
        title = {
            // Conditionally show a title or an icon
            if (title != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                }
            } else {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter =
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                        painterResource(R.drawable.ic_launcher_foreground) else
//                        painterResource(id = R.drawable.ic_launcher_foreground_legacy_foreground),
                    painterResource(R.mipmap.autotrac_logo),
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            if (onBackClick != null) IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            //HTTP Icon
            isSocketOnRemember?.let {
                BadgeText(text = "HTTP", isServiceOn = it)
            }
            if (apiIcon){
                BadgeText(text = "API", isServiceOn = isApiOn)
            }
            IconButton(
                onClick = {
                    drawerOpen = !drawerOpen
                },
                content = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            )
        }
    )
    Box {
        if (drawerOpen) {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.ExitToApp, contentDescription = "Log Out") },
                    label = { Text("Log Out") },
                    selected = selectedItem.value == selectedItem.value,
                    onClick = {
                        drawerOpen = false
                        popUpToLogin()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))

                LogContent() // Include your log content composable
            }
        }
    }
}

