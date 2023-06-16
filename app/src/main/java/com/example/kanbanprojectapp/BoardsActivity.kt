package com.example.kanbanprojectapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.kanbanprojectapp.databinding.ActivityBoardsBinding

class BoardsActivity : AppCompatActivity() {

    private lateinit var boardsAdapter: BoardsAdapter
    private lateinit var binding: ActivityBoardsBinding
    private lateinit var boards: ArrayList<Board>

    private fun getAndIncrementBoardId(): Int {
        val sharedPreferences = getSharedPreferences("boards", Context.MODE_PRIVATE)
        val currentId = sharedPreferences.getInt("currentBoardId", 1)
        sharedPreferences.edit().putInt("currentBoardId", currentId + 1).apply()
        return currentId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("boards", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("currentBoardId", 1).apply()

        binding = ActivityBoardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this@BoardsActivity)
        binding.recyclerView.layoutManager = linearLayoutManager

        boards = ArrayList()
        boardsAdapter = BoardsAdapter(this@BoardsActivity, boards)
        binding.recyclerView.adapter = boardsAdapter

        boards.add(Board(1, "Board 1", Color.RED, mutableListOf()))
        boards.add(Board(2, "Board 2", Color.BLUE, mutableListOf()))

        binding.addBoardButton.setOnClickListener {
            val id = getAndIncrementBoardId()
            boards.add(Board(id, "New Board", Color.GREEN, mutableListOf()))
            boardsAdapter.notifyItemInserted(boards.size - 1)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredBoards = boards.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }

                boardsAdapter.updateBoards(filteredBoards)
                return true
            }
        })

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
}