package com.example.mysample.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysample.R
import com.example.mysample.databinding.ChatListitemBinding
import com.example.mysample.model.ChatListsModel
import com.example.mysample.model.utilis.AppPreferences

class ChatListsAdapter(var context: Context, var chatsArraylist: MutableList<ChatListsModel>) :
    RecyclerView.Adapter<ChatListsAdapter.ChatListViewHolder>(), Filterable {

    private var selectedPos = -1
    var chatFilterList = ArrayList<ChatListsModel>()

    init {
        chatFilterList = chatsArraylist as ArrayList<ChatListsModel>

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListsAdapter.ChatListViewHolder {
        val chatlistItemBinding:ChatListitemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.chat_listitem, parent, false
        )
        return ChatListViewHolder(chatlistItemBinding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(chatsArraylist[position])
        if (selectedPos == position) {
            holder.chatlistItemBinding.selectedItemView.visibility = View.VISIBLE
        } else {
            holder.chatlistItemBinding.selectedItemView.visibility = View.INVISIBLE
        }
        //holder.bind(chatFilterList.get(position))
        Glide.with(context).load(AppPreferences.PIC_URL).into(holder.chatlistItemBinding.chatPicImg)


        holder.chatlistItemBinding.singleListItemRl.setOnClickListener {
            selectedPos = position
            notifyDataSetChanged()
            //Toast.makeText(context, "$selectedPos and $position and ${chatsArraylist.size}", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return chatsArraylist.size
    }

    inner class ChatListViewHolder(v: ChatListitemBinding) : RecyclerView.ViewHolder(v.root) {
         var chatlistItemBinding: ChatListitemBinding = v

        fun bind(chatslistModel: ChatListsModel) {
            chatlistItemBinding.viewModelList = chatslistModel
            /*chatlistItemBinding.companyNameTv.text = chatslistModel.company.name
            val bsText = chatslistModel.company.catchPhrase + chatslistModel.company.bs
            chatlistItemBinding.companyBsTv.text = bsText*/
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                val filteredList: ArrayList<ChatListsModel> = ArrayList()
                if (charString.isEmpty()) {
                    //chatFilterList.addAll(chatsArraylist)
                    filteredList.addAll(chatFilterList)
                } else {
                    val resultList = ArrayList<ChatListsModel>()
                    for (row in chatsArraylist) {
                        if (row.name.lowercase().contains(charString.lowercase())) {
                            //resultList.add(row)
                            filteredList.add(row)
                        }
                    }
                    //chatFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(charString: CharSequence?, results: FilterResults?) {
                chatsArraylist = results?.values as ArrayList<ChatListsModel>
                notifyDataSetChanged()
            }
        }
    }

}