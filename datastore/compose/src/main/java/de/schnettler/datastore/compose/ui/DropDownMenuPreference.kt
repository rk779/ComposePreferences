package de.schnettler.datastore.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.schnettler.datastore.compose.model.BasePreferenceItem.PreferenceItem.DropDownMenuPreferenceItem

@ExperimentalMaterialApi
@Composable
internal fun DropDownMenuPreference(
    item: DropDownMenuPreferenceItem,
    value: String,
    onValueChanged: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val close = { expanded.value = false }

    Preference(
        item = item,
        summary = item.entries[value],
        onClick = { expanded.value = true }
    )

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .padding(start = 64.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = close
        ) {
            item.entries.forEach { current ->
                val onSelected = {
                    onValueChanged(current.key)
                    close()
                }
                DropdownMenuItem(
                    onClick = onSelected
                ) {
                    Text(
                        text = current.value,
                        style = MaterialTheme.typography.body1.merge()
                    )
                }
            }
        }
    }
}