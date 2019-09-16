package com.github.ramonrabello.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ramonrabello.viewbinding.databinding.ActivityMainBinding
import com.github.ramonrabello.viewbinding.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        bindViews()
        setupObservers()
    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        with(binding) {
            setContentView(root)
            bindUserButton.setOnClickListener {
                viewModel.onBindUserButtonClicked(
                    nameField.text.toString().trim(),
                    surnameField.text.toString().trim()
                )
            }
            nameField.addTextChangedListener(viewModel.createNameFieldTextWatcher())
            surnameField.addTextChangedListener(viewModel.createSurnameFieldTextWatcher())
        }
    }

    private fun setupObservers() {
        viewModel.showToastLiveData.observe(this, Observer {
            Toast.makeText(this, "Welcome, ${it.message}", Toast.LENGTH_SHORT).show()
        })

        viewModel.buttonStateMediatorLiveData.observe(this, Observer { isEnabled ->
            binding.bindUserButton.isEnabled = isEnabled
        })
    }
}
