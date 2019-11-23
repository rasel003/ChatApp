package com.rasel.chatapp

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


class ChatFragment{

    private lateinit var viewModel: ChatViewModel

    private var chatAdapter = ChatAdapter(mutableListOf())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel.class);
        chatAdapter.data=viewModel.messages
        subscribeOnAddMessage()
    }

    private fun subscribeOnAddMessage() {
        viewModel.notifyNewMessageInsertedLiveData.observe(this, Observer {
            chatAdapter.notifyItemInserted(it)
        })
    }


    override fun initView() {
        super.initView()
        bot_conversation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                .apply {
                    stackFromEnd = true
                    isSmoothScrollbarEnabled = true
                }
            adapter = chatAdapter
        }

        input_layout.onSendClicked = {
            viewModel.sendMessage(it)
            hideKeyboard(activity)
        }
    }
}