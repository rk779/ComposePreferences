package de.schnettler.datastore.compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.accompanist.insets.statusBarsPadding
import de.schnettler.datastore.compose.LocalPreferenceEnabledStatus
import de.schnettler.datastore.compose.model.Preference
import de.schnettler.datastore.compose.model.Preference.PreferenceGroup
import de.schnettler.datastore.compose.model.Preference.PreferenceItem
import de.schnettler.datastore.manager.DataStoreManager
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun PreferenceScreen(
    items: List<Preference>,
    dataStore: DataStore<Preferences>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    statusBarPadding: Boolean = false,
    withGroupDivider: Boolean = false
) {
    val dataStoreManager = remember {
        DataStoreManager(dataStore)
    }

    PreferenceScreen(
        items = items,
        modifier = modifier,
        contentPadding = contentPadding,
        dataStoreManager = dataStoreManager,
        statusBarPadding = statusBarPadding,
        withGroupDivider = withGroupDivider
    )
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun PreferenceScreen(
    items: List<Preference>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    dataStoreManager: DataStoreManager,
    statusBarPadding: Boolean = false,
    withGroupDivider: Boolean = false
) {
    val prefs by dataStoreManager.preferenceFlow.collectAsState(initial = null)

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        if (statusBarPadding) {
            item { Spacer(modifier = Modifier.statusBarsPadding()) }
        }

        items.forEach { preference ->
            when (preference) {
                // Create Preference Group
                is PreferenceGroup -> {
                    item {
                        PreferenceGroupHeader(title = preference.title)
                    }
                    items(preference.preferenceItems) { item ->
                        CompositionLocalProvider(LocalPreferenceEnabledStatus provides preference.enabled) {
                            PreferenceItem(
                                item = item,
                                prefs = prefs,
                                dataStoreManager = dataStoreManager
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (withGroupDivider) {
                        item { Divider() }
                    }
                }

                // Create Preference Item
                is PreferenceItem<*> -> item {
                    PreferenceItem(
                        item = preference,
                        prefs = prefs,
                        dataStoreManager = dataStoreManager
                    )
                }
            }
        }
    }
}