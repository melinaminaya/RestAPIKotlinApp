package com.example.nanoclientkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nanoclientkotlin.dataRemote.Item
import com.example.nanoclientkotlin.navigation.NavGraph
import com.example.nanoclientkotlin.vm.MessageViewModel

/**
 * Classe mãe de inicialização do app teste.
 * Para mais informações sobre a integração, leia [README.md].
 * @author Melina Minaya
 */
class MainActivity : ComponentActivity() {
    private val viewModel: MessageViewModel by viewModels()
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
    override fun onDestroy() {
        super.onDestroy()
        // Close the connection
        NanoWebsocketClient.disconnect()
        // Close the HTTP client
        NanoHttpClient.client.dispatcher.cancelAll()
    }

    override fun onResume() {
        super.onResume()
        /**
         * Esse método é utilizado para reconectar ao servidor
         * quando o app é fechado e reiniciado.
         */
//        NanoWebsocketClient.connect()
    }
}

@Composable
fun NanoApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
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


