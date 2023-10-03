package com.example.nanoclientkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nanoclientkotlin.navigation.NavGraph
import com.example.nanoclientkotlin.security.SSLSetup
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
                    SSLSetup.loadTrustedCertificate(context = this)
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Close the connection
        NanoWebsocketClient.disconnect()
        // Close the HTTP client
//        NanoHttpClient.client.dispatcher.cancelAll()

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


