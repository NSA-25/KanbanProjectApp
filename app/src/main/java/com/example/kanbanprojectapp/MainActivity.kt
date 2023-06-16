package com.example.kanbanprojectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mainPage -> {
                    val Intent = Intent(this, BoardsActivity::class.java)
                    startActivity(Intent)
                    true
                }

                R.id.accountPage -> {
                    val Intent = Intent(this, AccountActivity::class.java)
                    startActivity(Intent)
                    true
                }

                R.id.settingsPage -> {
                    val Intent = Intent(this, SettingsActivity::class.java)
                    startActivity(Intent)
                    true
                }
                else -> false
            }
        }

    }
}