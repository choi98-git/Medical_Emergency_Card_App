package com.example.medicalemergencycardapp

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.medicalemergencycardapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_spinner_item
        )

        binding.birthdateLayer.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dateOfMonth ->
                binding.birthdateValueTextView.text = "$year-${month+1}-$dateOfMonth"
            }
            DatePickerDialog(this, listener,2000,0,1).show()
        }

        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
                binding.warningValueEditText.isVisible = isChecked
        }

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }

    }

    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()){
            putString(NAME,binding.nameValueEditText.text.toString())
            putString(BLOOD_TYPE,getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyContactValueEditText.text.toString())
            putString(BIRTHDATE, binding.birthdateValueTextView.text.toString())
            putString(WARNING, getWarning())
            apply()
        }
        Toast.makeText(this,"정보가 저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String{
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSigh = if (binding.bloodTypePlus.isChecked) "RH+" else "RH-"
        return "$bloodSigh ${bloodAlphabet}형"
    }

    private fun getWarning():String{
       return if (binding.warningCheckBox.isChecked) binding.warningValueEditText.text.toString() else ""
    }

}