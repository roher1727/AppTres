package com.clase2503.apptres.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clase2503.apptres.R
import com.clase2503.apptres.databinding.ActivityMain3Binding
import com.clase2503.apptres.databinding.ActivityMainBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}