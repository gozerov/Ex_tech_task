package ru.gozerov.ex_tech_task.screens.contacts_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import ru.gozerov.core.ActionListener
import ru.gozerov.core.BaseFragment
import ru.gozerov.core.appComponent
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.ex_tech_task.R
import ru.gozerov.ex_tech_task.databinding.FragmentContactsListBinding
import ru.gozerov.ex_tech_task.screens.contacts_list.FilterContactsDialog.Companion.ARG_FILTER
import ru.gozerov.ex_tech_task.screens.contacts_list.FilterContactsDialog.Companion.setupListener
import ru.gozerov.ex_tech_task.screens.contacts_list.state.ContactsViewState
import ru.gozerov.ex_tech_task.screens.user_profile.UserProfileFragment.Companion.ARG_USER_ID
import javax.inject.Inject

class ContactsListFragment: BaseFragment<ContactsViewState>() {

    private lateinit var binding: FragmentContactsListBinding

    private val contactsVPAdapter = ContactsVPAdapter()

    private val viewModel: ContactsViewModel by viewModels { factory }

    @Inject
    lateinit var factory: ContactsViewModel.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private var filter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBar.clearFocus()
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                renderState(state)
            }
        }

        setupListener(parentFragmentManager, viewLifecycleOwner) { filter ->
            viewModel.sortUsers(filter)

        }
        binding.filterContactsButton.setOnClickListener {
            findNavController().navigate(R.id.action_contactsListFragment_to_filterContactsDialog, bundleOf(
                ARG_FILTER to filter
            ))
        }


        binding.cancelButton.setOnClickListener {
            binding.searchBar.setQuery("", false)
            binding.searchBar.clearFocus()
            binding.cancelButton.visibility = View.GONE
        }

        binding.searchBar.setOnQueryTextFocusChangeListener { v, isFocused ->
            if (v.id == R.id.searchBar && isFocused) {
                binding.filterContactsButton.visibility = View.GONE
            } else {
                if (binding.searchBar.query.isBlank())
                    binding.filterContactsButton.visibility = View.VISIBLE
            }
        }

        binding.searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()
                binding.filterContactsButton.visibility = View.GONE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotBlank()) {
                        viewModel.filterUsers(it)
                        binding.cancelButton.visibility = View.VISIBLE
                    }
                    else {
                        binding.cancelButton.visibility = View.GONE
                        viewModel.filterUsers(newText)
                    }
                }
                return false
            }

        })

    }

    override fun renderState(state: ContactsViewState) {
        when(state) {
            is ContactsViewState.Loading -> {
                binding.root.children.forEach { view ->
                    if (view.id == R.id.layout_state_error || view.id == R.id.cancelButton)
                        view.visibility = View.GONE
                    else
                        view.visibility = View.VISIBLE
                }
            }

            is ContactsViewState.SuccessLoaded -> {
                if (binding.layoutStateError.root.visibility == View.VISIBLE)
                    binding.layoutStateError.root.visibility = View.GONE

                if (binding.cancelButton.visibility == View.GONE && binding.searchBar.query.isNotBlank())
                    binding.cancelButton.visibility = View.VISIBLE

                filter = state.currentFilter
                lifecycleScope.launch {
                    state.data.collect { users ->
                        setupViewPager(users)
                    }
                }
            }

            is ContactsViewState.Error -> {
                binding.root.children.forEach { view ->
                    if (view.id == R.id.layout_state_error)
                        view.visibility = View.VISIBLE
                    else
                        view.visibility = View.GONE
                }
            }

        }
    }

    private fun setupViewPager(users: Map<String, List<UserSimple>>) {
        val tabs = users.keys

        val adapters = tabs.map { key ->
            ContactsListAdapter(object : ActionListener<Int> {
                override fun onClick(args: Int) {
                    findNavController().navigate(R.id.action_contactsListFragment_to_userProfileFragment, bundleOf(
                        ARG_USER_ID to args
                    ))
                }

            }).apply {
                data = users.getValue(key)
            }
        }
        contactsVPAdapter.data =  adapters
        with(binding.contactsViewPager) {
            adapter = contactsVPAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(binding.tabLayout, binding.contactsViewPager) { tab, position ->
            tab.text = tabs.elementAt(position)
        }.attach()

        binding.contactsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                if (binding.searchBar.query.isBlank()) {
                    binding.searchBar.clearFocus()
                    binding.filterContactsButton.visibility = View.VISIBLE
                }
                super.onPageSelected(position)
            }

        })
    }

}