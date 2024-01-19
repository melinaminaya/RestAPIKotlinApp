package br.com.autotrac.testnanoclient.screens

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.autotrac.testnanoclient.R
import br.com.autotrac.testnanoclient.common.DropdownCard
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import com.google.gson.Gson

/**
 * Filtro de seleção de requisições da tela HttpTest.
 * @see HttpTestScreen
 * @author Melina Minaya
 */
@Composable
fun FilterSelectionBox(
    optionSelected: String,
    param1: String,
    param2: String,
    param3: String,
    param4: String,
    onParamsChanged: (String, String, String, String) -> Unit
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val gson = Gson()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf<String>("") }
    val fileList = remember { mutableStateListOf<Uri>() }

    LaunchedEffect(selectedFileName){
        if(selectedFileName != "") {
            onParamsChanged(param1, param2, selectedFileName, param4)
            fileList.clear()
            fileList.add(selectedFileUri!!)
        }
    }

    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        DropdownCard(title = "Filtro") {

            Spacer(modifier = Modifier.height(16.dp))
            when (optionSelected) {
                ApiEndpoints.REQ_MESSAGE_COUNT ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (isForward)") },
                        singleLine = true,
                        placeholder ={

                                Text(text = "false: para mensagens de entrada \n" +
                                        "true: para mensagens de saída",
                                    color = androidx.compose.ui.graphics.Color.Gray,
                                    fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (msgStatusNum)") },
                        singleLine = true,
                        placeholder ={
                                Text(text = "Ver MessageStatusValues \n" +
                                        "exemplo: 3 -> NOT_READ",
                                    color = androidx.compose.ui.graphics.Color.Gray,
                                    fontSize = 12.sp
                                )
                        }
                    )
                }
                ApiEndpoints.REQ_MESSAGE_LIST ->{
                    // Text field for 'param1' parameter
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (msgCode)") },
                        singleLine = true,
                        placeholder ={
                                Text(text = "0: código de mensagem ignorado ",
                                    color = androidx.compose.ui.graphics.Color.Gray,
                                    fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (isForward)") },
                        singleLine = true,
                        placeholder ={
                                Text(text = "false: para mensagens de entrada \n" +
                                        "true: para mensagens de saída",
                                    color = androidx.compose.ui.graphics.Color.Gray,
                                    fontSize = 12.sp
                                )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param3,
                        onValueChange = { onParamsChanged(param1, param2, it, param4) },
                        label = { Text("Param3: (msgStatusNum)") },
                        singleLine = true,
                        placeholder ={
                                Text(text = "Ver MessageStatusValues \n" +
                                        "exemplo: 3 -> NOT_READ",
                                    color = androidx.compose.ui.graphics.Color.Gray,
                                    fontSize = 12.sp )
                            }
                    )
                }
                ApiEndpoints.REQ_MESSAGE_DELETE ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (msgCode)") },
                        singleLine = true,
                        placeholder ={

                            Text(text = "Código da mensagem a ser deletada",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.REQ_MESSAGE_SET_AS_READ ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (msgCode)") },
                        singleLine = true,
                        placeholder ={

                            Text(text = "Código da mensagem a ser definida como lida",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.REQ_FILE_OPERATION ->{
                    // Text field for 'param1' parameter
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (files)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ver ActionValues.FileOperationFiles \n" +
                                    "exemplo: 2 -> SVC_DATABASE",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (options)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ver ActionValues.FileOperationOptions \n" +
                                    "exemplo: 1 -> COPY_FILES",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param3,
                        onValueChange = { onParamsChanged(param1, param2, it, param4) },
                        label = { Text("Param3: (destination)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "/storage/emulated/0/Download/AutotracMobile/logs.zip",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param4,
                        onValueChange = { onParamsChanged(param1, param2, param3, it) },
                        label = { Text("Param4: (timeoutMs)" ) },
                        singleLine = true,
                        placeholder ={
                            Text(text = "10000",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.REQ_CONFIG_SERVICE_LOG ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (enable)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "true: para ativar a geração de log",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (maxFileCount)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "ex: 2",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param3,
                        onValueChange = { onParamsChanged(param1, param2, it, param4) },
                        label = { Text("Param3: (maxFileSize)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "ex: 5242880 == 5MG ",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp )
                        }
                    )
                }
                ApiEndpoints.REQ_FORM_LIST->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (groupOnly)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "false: não especifica grupos",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (formGroupCode)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "0: filtro desabilitado",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param3,
                        onValueChange = { onParamsChanged(param1, param2, it, param4) },
                        label = { Text("Param3: (formType)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ver ActionValues.FormTypeValues \n" +
                                    "1: FIXED_TO_MOBILE_TXT",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp )
                        }
                    )
                }
                ApiEndpoints.REQ_GET_POSITION_LAST->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (posSourceType)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = " Ver ActionValues.PositionSourceType \n" +
                                    "ex: 0 - GPS ou 1 - MCT",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.REQ_POSITION_HISTORY_COUNT ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (msgStatusNum)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ver ActionValues.MessageStatusValues \n" +
                                    "exemplo: 2 -> SENT",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.REQ_POSITION_HISTORY_LIST ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (posCode)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Código da posição a ser retornada." +
                                    "ex: 0 - parametro será ignorado.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: (msgStatusNum)") },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Ver ActionValues.MessageStatusValues \n" +
                                        "exemplo: 1 -> TO_SEND, " +
                                        "2 -> SENT",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_EXT_DEV_COMM_TYPE ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (External Device Communication Type)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ver ParameterValues.ExternalDeviceCommunicationTypeValues" +
                                    "Ex.: 2 - para realizar o batismo",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Alternative Communication Device Poll Interval in Seconds)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ex.: 10 - intervalo em segundos recomendado.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Habilitação da VPN)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ex.: 0 - habilita configuração VPN.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_OUT_OF_BAND_MSG_PATH ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Diretório de arquivos de mensagens longas)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ex.: /storage/emulated/0/AutotracMobile/",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_WIFI_SSID ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Configuração do SSID)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Atenção: O SSID só pode ser configurado para produtos que possuem comunicação exclusiva satélite." +
                                    "Configura o batismo. Caso vazio, batismo será desfeito." +
                                    "Ex.: SAFESOFT0123456",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Habilitação do WIFI)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Ex.: 0 - Habilita o WIFI",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG ->{
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: (Timeout para envio de mensagem.)") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Atenção: Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular." +
                                    "Ex.: 30 - valor em segundos recomendado.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }
                ApiEndpoints.SEND_MESSAGE ->{
                    TextField(
                        value = param1,
                        onValueChange = {onParamsChanged(it, param2, param3, param4)},
                        label = { Text("Param1: (Message) ") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Corpo da mensagem.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                }

                /**
                 * Endpoint com envio via websocket.
                 */
                ApiEndpoints.SEND_FILE_MESSAGE ->{
                    TextField(
                        value = param1,
                        onValueChange = {
                                onParamsChanged(it, param2, param3, param4)
                        },
                        label = { Text("Param1: (Message) ") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "Corpo da mensagem.",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                    FilePicker(
                        buttonSend = false,
                        selectedFileStringPicker = {
                            selectedFileUri = it
                            onParamsChanged(param1, param2, param3, selectedFileUri.toString())
                        },
                        selectedFileName = {
                            selectedFileName = it
                            selectedFileUri?.let { it1 -> fileList.add(it1) }
                        },
                        onSendMessage = {""},
                        navigateToInbox = null,
                        snackbarHost = null,
                        addFile = {}
                    )
                    if(selectedFileUri != null){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_attach_file_24),
                                contentDescription = "Attach File",
                            )
                            Text(
                                text = getFileNameFromUri(selectedFileUri!!, context),
                                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                else -> {
                    TextField(
                        value = param1,
                        onValueChange = { onParamsChanged(it, param2, param3, param4) },
                        label = { Text("Param1: ") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param2,
                        onValueChange = { onParamsChanged(param1, it, param3, param4) },
                        label = { Text("Param2: ") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param3,
                        onValueChange = { onParamsChanged(param1, param2, it, param4) },
                        label = { Text("Param3: ") },
                        singleLine = true,
                        placeholder ={
                            Text(text = "",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text field for 'param2' parameter
                    TextField(
                        value = param4,
                        onValueChange = { onParamsChanged(param1, param2, param3, it) },
                        label = { Text("Param4: " ) },
                        singleLine = true,
                        placeholder ={
                            Text(text = "",
                                color = androidx.compose.ui.graphics.Color.Gray,
                                fontSize = 12.sp)
                        }
                    )
                }

            }

        }
    }
}
