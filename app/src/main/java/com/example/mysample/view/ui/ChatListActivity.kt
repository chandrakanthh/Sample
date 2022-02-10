package com.example.mysample.view.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysample.R
import com.example.mysample.databinding.ActivityChatListBinding
import com.example.mysample.model.services.ApiService
import com.example.mysample.model.ChatListsModel
import com.example.mysample.model.utilis.AppPreferences
import com.example.mysample.model.repos.ChatListRepository
import com.example.mysample.model.base.ChatListViewModelFactory
import com.example.mysample.model.services.ServiceBuilder
import com.example.mysample.view.adapter.ChatListsAdapter
import com.example.mysample.viewModel.ChatListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatListActivity : AppCompatActivity() {

    private lateinit var chatListBinding: ActivityChatListBinding
    private lateinit var chatListsAdapter: ChatListsAdapter
    private var chatsArrayList: ArrayList<ChatListsModel> = ArrayList()
    lateinit var context: Context
    lateinit var chatListViewModel: ChatListViewModel
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatListBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_list)
        context = this
        AppPreferences.init(context)
        toggle = ActionBarDrawerToggle(
            this,
            chatListBinding.drawerLayout,
            chatListBinding.customlayer.customToolbarTb,
            0,
            0
        )
        chatListBinding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setToolbarNavigationClickListener {
            chatListBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        toggle.setHomeAsUpIndicator(R.drawable.nav_menu_icon)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //setSupportActionBar(chatListBinding.customlayer.customToolbarTb)

        chatListBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout_it -> {
                    AppPreferences.clearAll()
                    navTologinPage()
                    true
                }
                else -> false
            }
        }
        Toast.makeText(this, "Welcome ${AppPreferences.userEmail}", Toast.LENGTH_SHORT).show()

        //load all chat list data without using view model
        //loadChatListData()

        //to search items in recycler view
        chatListBinding.searchChatList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false }
            override fun onQueryTextChange(newText: String?): Boolean {
                chatListsAdapter.filter.filter(newText)
                return false }
        })


        chatListViewModel = ViewModelProvider(this,
            ChatListViewModelFactory(ChatListRepository(ServiceBuilder.buildService(ApiService::class.java)))
        ).get(ChatListViewModel::class.java)
        chatListViewModel.chatLists.observe(this, Observer {
            loadAllChatData(it)
        })
        chatListViewModel.getChatListData()
    }

    private fun loadAllChatData(it: List<ChatListsModel>?) {
        //val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val llm = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        chatListBinding.chatsListRv.layoutManager = llm
        chatsArrayList = it!! as ArrayList<ChatListsModel>
        chatListsAdapter = ChatListsAdapter(context, chatsArrayList)
        chatListBinding.chatsListRv.adapter = chatListsAdapter
        val dividerItemDecoration = DividerItemDecoration(context, llm.orientation)
        chatListBinding.chatsListRv.addItemDecoration(dividerItemDecoration)
    }

    //nav to login
    private fun navTologinPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun loadChatListData() {
        val userService = ServiceBuilder.buildService(ApiService::class.java)
        val requestCall = userService.getChatListData()
        requestCall.enqueue(object : Callback<List<ChatListsModel>> {

            override fun onResponse(
                call: Call<List<ChatListsModel>>, response: Response<List<ChatListsModel>>
            ) {
                if (response.isSuccessful) {
                    val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    chatListBinding.chatsListRv.layoutManager = llm
                    chatsArrayList = response.body()!! as ArrayList<ChatListsModel>
                    chatListsAdapter = ChatListsAdapter(context, chatsArrayList)
                    chatListBinding.chatsListRv.adapter = chatListsAdapter
                    val dividerItemDecoration = DividerItemDecoration(context, llm.orientation)
                    chatListBinding.chatsListRv.addItemDecoration(dividerItemDecoration)
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ChatListsModel>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_items, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_btn -> Toast.makeText(this, "On nav bar clicked", Toast.LENGTH_SHORT)
                .show()
        }

        return false
    }
}