package com.example.mycbandoeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var mySearchView: SearchView

    lateinit var newAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val linkJson = applicationContext.assets
            .open("cctv_link.json").bufferedReader().use {
                it.readText()
            }

        val link = Gson().fromJson(linkJson, CctvLink::class.java)

        newAdapter = Adapter(link)
        newAdapter.onItemClick = {
            val intent = Intent(this, CctvPage::class.java)
            intent.putExtra("title", it.title)
            intent.putExtra("link", it.arguments[0])
            startActivity(intent)
        }

        newRecyclerView = findViewById(R.id.list_cctv)
        newRecyclerView.layoutManager =LinearLayoutManager(this)
        newRecyclerView.adapter = newAdapter

        mySearchView = findViewById(R.id.search_bar)
        mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query : String?) {
        val linkJson = applicationContext.assets
            .open("cctv_link.json").bufferedReader().use {
                it.readText()
            }

        val link = Gson().fromJson(linkJson, CctvLink::class.java)

        if (query != null) {
            val filteredList = ArrayList<CctvLinkItem>()
            for (i in link){
                if (i.title.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this, "CCTV Not Found", Toast.LENGTH_SHORT).show()
            }
            else {
                newAdapter.setFilteredList(filteredList)
            }
        }
    }
}