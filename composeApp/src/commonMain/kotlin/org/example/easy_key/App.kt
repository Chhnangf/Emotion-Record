package org.example.easy_key

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import emotion_record.composeapp.generated.resources.Res
import emotion_record.composeapp.generated.resources.compose_multiplatform

import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Composable
@Preview
fun App() {

    MaterialTheme {
        Navigator(HomeScreen())

    }
}