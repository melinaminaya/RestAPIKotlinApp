package br.com.autotrac.testnanoclient.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.autotrac.testnanoclient.models.CheckList

@Composable
fun CheckListCard(content: List<CheckList>?) {
    if (content != null) {
        Spacer(modifier = Modifier.height(8.dp))
        content.forEach { item ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cellular Status: ${item.cellularStatus}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Cellular Signal: ${item.cellularSignalLevel}%",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Satellite Status and Signal: ${item.hasSatelliteSignal} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Wifi Status: ${item.wifiStatus} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Wifi Signal: ${item.wifiSignal}%",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Last GPS Position Date: ${item.lastGpsPosDate} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Last Satellite Communication Date: ${item.lastCommSatellite} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Last Cellular Communication Date: ${item.lastCommCellular} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Service Version: ${item.serviceVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Interface Version: ${item.interfaceVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "DatabaseVersion: ${item.databaseVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "SO System Version: ${item.soSystemVersion} ",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }

}