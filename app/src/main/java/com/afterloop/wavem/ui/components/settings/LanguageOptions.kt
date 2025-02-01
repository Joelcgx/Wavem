package com.afterloop.wavem.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.afterloop.wavem.R

data class LanguageOptions(
    val langCode: String,
    val language: String,
    val description: String
) {
    companion object {
        @Composable
        fun getLanguages(): List<LanguageOptions> = listOf(
            LanguageOptions(
                langCode = "en",
                language = stringResource(R.string.language_en),
                description = stringResource(R.string.language_en)
            ),
            LanguageOptions(
                langCode = "es",
                language = stringResource(R.string.language_es),
                description = stringResource(R.string.language_es)
            )
        )
    }
}
