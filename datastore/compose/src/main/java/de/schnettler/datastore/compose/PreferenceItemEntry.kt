package de.schnettler.datastore.compose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.preferences.core.Preferences
import de.schnettler.datastore.compose.model.BasePreferenceItem.PreferenceItem
import de.schnettler.datastore.compose.model.BasePreferenceItem.PreferenceItem.*
import de.schnettler.datastore.compose.ui.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PreferenceItemEntry(item: PreferenceItem<*>, prefs: Preferences?) {
    val scope = rememberCoroutineScope()
    val dataStore = LocalDataStoreManager.current

    when (item) {
        is SwitchPreferenceItem -> {
            SwitchPreference(
                item = item,
                value = prefs?.get(item.metaData.dataStoreKey) ?: item.metaData.defaultValue,
                onValueChanged = { newValue ->
                    scope.launch() { dataStore.editPreference(item.metaData, newValue) }
                }
            )
        }
        is RadioBoxListPreferenceItem -> {
            ListPreference(
                item = item,
                value = prefs?.get(item.metaData.dataStoreKey) ?: item.metaData.defaultValue,
                onValueChanged = { newValue ->
                    scope.launch { dataStore.editPreference(item.metaData, newValue) }
                }
            )
        }
        is CheckBoxListPreferenceItem -> {
            MultiSelectListPreference(
                item = item,
                values = prefs?.get(item.metaData.dataStoreKey) ?: item.metaData.defaultValue,
                onValuesChanged = { newValues ->
                    scope.launch { dataStore.editPreference(item.metaData, newValues) }
                }
            )
        }
        is SeekBarPreferenceItem -> {
            SeekBarPreference(
                item = item,
                value = prefs?.get(item.metaData.dataStoreKey) ?: item.metaData.defaultValue,
                onValueChanged = { newValue ->
                    scope.launch { dataStore.editPreference(item.metaData, newValue) }
                },
            )
        }
        is BasicPreferenceItem -> {
            Preference(
                item = item,
                onClick = item.onClick,
            )
        }
        is DropDownMenuPreferenceItem -> {
            DropDownMenuPreference(
                item = item,
                value = prefs?.get(item.metaData.dataStoreKey) ?: item.metaData.defaultValue,
                onValueChanged = { newValue ->
                    scope.launch { dataStore.editPreference(item.metaData, newValue) }
                }
            )
        }
    }
}