package com.afterloop.wavem.viewmodel.ui.components.bottombars

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class BottomBarsVM @Inject constructor() : ViewModel() {
    private val _selectedIndex = MutableStateFlow<Int>(0)
    val selectedIndex: MutableStateFlow<Int> = _selectedIndex

    fun setSelectedIndex(index: Int) {
        _selectedIndex.value = index
    }
}