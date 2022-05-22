package ru.gozerov.ex_tech_task.screens.contacts_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.gozerov.core.ActionListener
import ru.gozerov.core.BaseRecyclerViewAdapter
import ru.gozerov.core.BaseViewHolder
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.ex_tech_task.R
import ru.gozerov.ex_tech_task.databinding.ItemContactBinding

class ContactsListAdapter(
    private val actionListener: ActionListener<Int>
): BaseRecyclerViewAdapter<UserSimple>(), View.OnClickListener {

    class ViewHolder(private val binding: ItemContactBinding) : BaseViewHolder<UserSimple>(binding) {
        override fun bind(data: UserSimple) {
            with(binding) {
                if (data.image!=null) {
                    Glide.with(this.root.context)
                        .load(data.image)
                        .into(imgContact)

                } else
                    imgContact.setImageResource(R.drawable.flying_saucer)
                txtContactName.text = data.name
                txtContactProfession.text = data.profession
            }
        }
    }

    override var data: List<UserSimple> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserSimple> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onClick(view: View?) {
        try {
            actionListener.onClick((view?.tag as UserSimple).id)
        } catch (e: Exception) {
            Log.e("ADAPTER", e.stackTrace.toString())
        }
    }
}