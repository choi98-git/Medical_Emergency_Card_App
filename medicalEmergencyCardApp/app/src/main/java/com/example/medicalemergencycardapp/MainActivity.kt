package com.example.medicalemergencycardapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.medicalemergencycardapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.inputValueButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.delteValueButton.setOnClickListener {
            deleteInformation()
        }

        binding.warningValueTextView.isVisible = binding.warningCheckBox.isChecked

        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.warningValueTextView.isVisible = isChecked
        }
    }

    override fun onResume() {
        super.onResume()
        getDataAndUiUpdate()
    }

    private fun getDataAndUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.nameValueTextView.text = getString(NAME,"미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val warning = getString(WARNING,"")

            binding.warningCheckBox.isVisible = warning.isNullOrEmpty().not()
            binding.warningCheckBox.isChecked = warning.isNullOrEmpty().not()
            binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warningTextView.isVisible = warning.isNullOrEmpty().not()

            if (warning.isNullOrEmpty().not()){
                binding.warningValueTextView.text = warning
            }
        }
    }

    private fun deleteInformation(){
        with(getSharedPreferences(USER_INFORMATION,Context.MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataAndUiUpdate()
        }
        Toast.makeText(this,"정보가 초기화되었습니다.",Toast.LENGTH_SHORT).show()
    }
}