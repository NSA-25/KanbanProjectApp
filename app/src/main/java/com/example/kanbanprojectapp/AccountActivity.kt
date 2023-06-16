package com.example.kanbanprojectapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.kanbanprojectapp.databinding.ActivityAccountBinding
import com.example.kanbanprojectapp.databinding.ActivityBoardsBinding
import java.io.File

class AccountActivity : AppCompatActivity(), ImagePickerBottomSheetFragment.ImagePickerListener {

    private lateinit var profileImageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var binding: ActivityAccountBinding

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            profileImageView.setImageURI(photoUri)
        }
    }

    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileImageView = findViewById(R.id.profileImageView)
        nameEditText = findViewById(R.id.nameEditText)

        profileImageView.setOnClickListener {
            val bottomSheetFragment = ImagePickerBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mainPage -> {
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    true
                }
                R.id.accountPage -> {
                    val accountIntent = Intent(this, AccountActivity::class.java)
                    startActivity(accountIntent)
                    true
                }
                R.id.settingsPage -> {
                    val settingsIntent = Intent(this, SettingsActivity::class.java)
                    startActivity(settingsIntent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionSelected(option: ImagePickerBottomSheetFragment.ImageOption) {
        when (option) {
            ImagePickerBottomSheetFragment.ImageOption.CAMERA -> takePhoto()
            else -> {}
        }
    }

    private fun takePhoto() {
        val photoFile = File(externalCacheDir, "temp_photo.jpg")
        photoUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        takePictureLauncher.launch(photoUri)
    }
}