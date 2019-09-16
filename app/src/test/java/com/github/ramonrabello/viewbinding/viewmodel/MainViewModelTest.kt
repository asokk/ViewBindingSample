package com.github.ramonrabello.viewbinding.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.ramonrabello.viewbinding.viewstates.ShowToast
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel by lazy { MainViewModel() }

    @Test
    fun `When both fields are not blank, mediator live data should emit true`() {
        val nameFieldTextWatcher = viewModel.createNameFieldTextWatcher()
        val surnameFieldTextWatcher = viewModel.createSurnameFieldTextWatcher()

        nameFieldTextWatcher.onTextChanged("Ramon", 0, 0, 5)
        surnameFieldTextWatcher.onTextChanged("Rabello", 0, 0, 7)

        viewModel.buttonStateMediatorLiveData.observeForever { assertTrue(it) }
    }

    @Test
    fun `When only name field is blank, button mediator live data should emit false`() {
        val nameFieldTextWatcher = viewModel.createNameFieldTextWatcher()
        val surnameFieldTextWatcher = viewModel.createSurnameFieldTextWatcher()

        nameFieldTextWatcher.onTextChanged("", 0, 0, 0)
        surnameFieldTextWatcher.onTextChanged("Rabello", 0, 0, 7)

        viewModel.buttonStateMediatorLiveData.observeForever { assertFalse(it) }
    }

    @Test
    fun `When only surname field is blank, button mediator live data should emit false`() {
        val nameFieldTextWatcher = viewModel.createNameFieldTextWatcher()
        val surnameFieldTextWatcher = viewModel.createSurnameFieldTextWatcher()

        nameFieldTextWatcher.onTextChanged("Ramon", 0, 0, 5)
        surnameFieldTextWatcher.onTextChanged("", 0, 0, 0)

        viewModel.buttonStateMediatorLiveData.observeForever { assertFalse(it) }
    }

    @Test
    fun `When bind user button clicked, should check if ShowToast was emitted`() {
        val name = "Ramon"
        val surname = "Rabello"
        viewModel.onBindUserButtonClicked(name, surname)

        assertEquals(viewModel.showToastLiveData.value, ShowToast("$name $surname"))
    }
}