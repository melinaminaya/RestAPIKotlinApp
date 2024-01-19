package br.com.autotrac.testnanoclient.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.vm.ParametersViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownToSet(
    title: String,
    previousText: String?,
    onTextChange: (String) -> Unit,
    textStatus: String?,
    dropdownItems: List<String?>,
) {
    val dropdownExpanded = remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(textStatus) }
    LaunchedEffect(textStatus){
        selectedOptionText = textStatus
    }

    Text(
        text = title,
        style = TextStyle(fontSize = 14.sp),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .wrapContentWidth(Alignment.Start)
    )
    Spacer(modifier = Modifier.width(2.dp))
    if ((previousText != null) && (selectedOptionText != null)) {
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded.value,
            onExpandedChange = { dropdownExpanded.value = it },
            modifier = Modifier
                .wrapContentWidth()
        ) {

            TextField(
                readOnly = true,
                value = selectedOptionText!!,
                onValueChange = { },
//                    label = { Text(title) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = dropdownExpanded.value
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded.value,
                onDismissRequest = {
                    dropdownExpanded.value = false
                }
            ) {
                dropdownItems.forEachIndexed() { index, selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption!!
                            onTextChange(index.toString())
                            dropdownExpanded.value = false
                        },
                        text = { Text(text = selectionOption ?: "N/A", fontSize = 14.sp) }
                    )
                }
            }
        }
    } else {
        LoadingIcon(size = 25, null)
    }
}