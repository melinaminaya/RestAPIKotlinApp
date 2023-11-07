package br.com.autotrac.testnanoclient.common

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.autotrac.testnanoclient.R
import br.com.autotrac.testnanoclient.screens.getFileNameFromUri

@Composable
fun CustomAttachFile(
    fileList: List<Uri>?,
    deletedFileName:(Uri)->Unit,
){
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        if (fileList != null) {
            for (item in fileList) {
                item {
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
                            text = getFileNameFromUri(item, context),
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                //                        selectedFileName = null // Clear the selected file
                                deletedFileName(item)
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription ="Remove File" )
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    // Rest of your content goes here
                    // ...

                }
            }
        }
    }
}