package ru.gozerov.ex_tech_task.screens.contacts_list

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.gozerov.core.*
import ru.gozerov.ex_tech_task.databinding.ItemContactVpBinding

class ContactsVPAdapter: BaseRecyclerViewAdapter<ContactsListAdapter>() {

    class ContactsViewHolder(private val binding: ItemContactVpBinding): BaseViewHolder<ContactsListAdapter>(binding) {

        override fun bind(data: ContactsListAdapter) {
            binding.contactsRecyclerView.setupAdapter(
                adapter = data,
                data = data.data,
                layoutManager = LayoutManagerRV.LINEAR(ORIENTATION.VERTICAL)
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ContactsListAdapter> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactVpBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override var data: List<ContactsListAdapter> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
}