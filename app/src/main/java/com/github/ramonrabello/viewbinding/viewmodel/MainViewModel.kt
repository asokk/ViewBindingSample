package com.github.ramonrabello.viewbinding.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ramonrabello.viewbinding.model.User
import com.github.ramonrabello.viewbinding.viewstates.ShowToast

class MainViewModel : ViewModel() {

    private val _showToastLiveData = MutableLiveData<ShowToast>()
    private val _nameFieldLiveData = MutableLiveData<Boolean>()
    private val _surnameFieldLiveData = MutableLiveData<Boolean>()
    private val _buttonStateMediatorLiveData = MediatorLiveData<Boolean>()

    val showToastLiveData: LiveData<ShowToast>
        get() = _showToastLiveData

    val buttonStateMediatorLiveData: LiveData<Boolean>
        get() = _buttonStateMediatorLiveData

    init {
        // initially emmit false for both LiveDatas in order to
        // disable button
        _nameFieldLiveData.value = false
        _surnameFieldLiveData.value = false

        // add MediatorLiveData sources for emitting field content changes.
        // The button should only be visible when both name and surname fields
        // content are not blank
        _buttonStateMediatorLiveData.addSource(_nameFieldLiveData) {
            _buttonStateMediatorLiveData.value = it && _surnameFieldLiveData.value!!
        }
        _buttonStateMediatorLiveData.addSource(_surnameFieldLiveData) {
            _buttonStateMediatorLiveData.value = it && _nameFieldLiveData.value!!
        }
    }

    fun onBindUserButtonClicked(name: String, surname: String) {
        _showToastLiveData.value = ShowToast("${User(name, surname)}")
    }

    fun createNameFieldTextWatcher() = FieldTextWatcher(_nameFieldLiveData)

    fun createSurnameFieldTextWatcher() = FieldTextWatcher(_surnameFieldLiveData)

    class FieldTextWatcher(private val mutableLiveData: MutableLiveData<Boolean>) : TextWatcher {

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { mutableLiveData.value = s.isNotBlank() }
        }
    }
}