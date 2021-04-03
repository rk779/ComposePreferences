package de.schnettler.datastore.compose

import androidx.compose.runtime.*
import de.schnettler.datastore.manager.DataStoreManager

val LocalDataStoreManager: ProvidableCompositionLocal<DataStoreManager> =
    compositionLocalOf(structuralEqualityPolicy()) { error("No preferences found") }

@Composable
fun ProvideDataStoreManager(
    dataStoreManager: DataStoreManager,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDataStoreManager provides dataStoreManager) {
        content()
    }
}

val LocalPreferenceEnabledStatus: ProvidableCompositionLocal<Boolean> =
    compositionLocalOf(structuralEqualityPolicy()) { true }