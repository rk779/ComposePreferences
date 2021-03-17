package de.schnettler.composepreferences

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import de.schnettler.composepreferences.ui.ComposePreferencesTheme
import de.schnettler.datastore.compose.model.Preference
import de.schnettler.datastore.compose.model.Preference.PreferenceGroup
import de.schnettler.datastore.compose.model.Preference.PreferenceItem
import de.schnettler.datastore.compose.ui.PreferenceScreen
import kotlin.math.roundToInt

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localDataStore = this.dataStore

        val listGroup = PreferenceGroup(
            "List Group", false, listOf(
                PreferenceItem.ListPreference(
                    ListPrefExample,
                    title = "List Preference",
                    summary = "Select one item from a list in a dialog",
                    singleLineTitle = true,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    entries = mapOf(
                        "key1" to "Item1",
                        "key2" to "Item2"
                    ),
                ),
                PreferenceItem.MultiSelectListPreference(
                    MultiPrefExample,
                    title = "MultiSelect List Preference",
                    summary = "Select multiple items from a list in a dialog",
                    singleLineTitle = true,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    entries = mapOf(
                        "key1" to "Item1",
                        "key2" to "Item2"
                    ),
                ),
                PreferenceItem.DropDownMenuPreference(
                    DropDownPrefExample,
                    title = "DropDown Menu Preference",
                    summary = "Select an item from a dropdown menu",
                    singleLineTitle = true,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    entries = mapOf(
                        "key1" to "Item1",
                        "key2" to "Item2"
                    )
                )
            )
        )

        setContent {
            ComposePreferencesTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Compose Preferences") }
                        )
                    },
                    content = {
                        PreferenceScreen(
                            items = listOf(
                                PreferenceItem.SwitchPreference(
                                    SwitchPrefExample,
                                    title = "Switch Preference",
                                    summary = "A preference with a switch.",
                                    singleLineTitle = true,
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Warning,
                                            contentDescription = null,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                ),
                                listGroup,
                                PreferenceItem.SeekBarPreference(
                                    SeekPrefExample,
                                    title = "Seekbar Preference",
                                    summary = "Select a value on a seekbar",
                                    singleLineTitle = true,
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Warning,
                                            contentDescription = null,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    },
                                    steps = 4,
                                    valueRange = 50F..100F,
                                    valueRepresentation = { value -> "${value.roundToInt()} %" }
                                )
                            ),
                            dataStore = localDataStore
                        )
                    }
                )
            }
        }
    }
}