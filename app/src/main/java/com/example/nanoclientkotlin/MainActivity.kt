package com.example.nanoclientkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nanoclientkotlin.common.DefaultButton
import com.example.nanoclientkotlin.navigation.NavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NanoClientKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NanoApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NanoApp(){
    val itemClickHandler = ItemClickHandler()
    val context = LocalContext.current
    val navController = rememberNavController()
    NavGraph(navController = navController)
//    Scaffold(
//
//        topBar = {
//            NanoTopAppBar()
//        }
//
//    ) { it ->
//        LazyColumn(contentPadding = it) {
//            items(items) {
//                Item(
//                    item = it,
//                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
//                    onClick = {
//                            //Handle click
//                            itemClickHandler.handleClickEvent(context, it.name)
//                        }
//                )
//            }
//        }
//    }

}

@Preview(showBackground = true)
@Composable
fun NanoPreview() {
    NanoClientKotlinTheme {
        NanoClientKotlinTheme(darkTheme = true) {
            NanoApp()
        }

    }
}
/**
 * Composable that displays a list item containing a item icon and their information.
 *
 * @param item contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(modifier = modifier, onClick = onClick) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            ItemInformation(item.name, item.description)
        }
    }
}


/**
 * Composable that displays a item's name and description.
 *
 * @param itemName is the resource ID for the string of the item's name
 * @param itemDescription is the Int that represents the item's description
 * @param modifier modifiers to set to this composable
 */
@Composable
fun ItemInformation(
    @StringRes itemName: Int,
//    itemAge: Int,
    @StringRes itemDescription: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(itemName),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
        )
        Text(
            text = stringResource(R.string.item_description_1, itemDescription),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NanoTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        modifier = modifier
    )
}

