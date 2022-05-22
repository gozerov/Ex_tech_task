package ru.gozerov.ex_tech_task.screens.user_profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import ru.gozerov.core.BaseFragment
import ru.gozerov.core.appComponent
import ru.gozerov.ex_tech_task.databinding.FragmentUserProfileBinding
import ru.gozerov.ex_tech_task.screens.user_profile.state.UserViewState
import ru.gozerov.ex_tech_task.screens.user_profile.state.UserViewState.*
import javax.inject.Inject

class UserProfileFragment: BaseFragment<UserViewState>() {

    private lateinit var binding: FragmentUserProfileBinding

    private val viewModel: UserProfileViewModel by viewModels { factory }

    @Inject
    lateinit var factory: UserProfileViewModel.Factory

    private val userId: Int
        get() = requireArguments().getInt(ARG_USER_ID)

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                renderState(state)
            }
        }
    }

    override fun renderState(state: UserViewState) {
        when (state) {
            is Loading -> {
                viewModel.loadUser(userId)
            }
            is SuccessLoaded -> {
                with(binding) {
                    Glide.with(root.context)
                        .load(state.user.image)
                        .into(imgUser)

                    txtUserName.text = state.user.name
                    txtUserProfession.text = state.user.profession
                    txtUserBirthday.text = state.user.birthday.toString()
                    txtUserAge.text = state.user.age
                    txtUserPhone.text = state.user.phone
                }
            }
            is Error -> {

            }
        }
    }

    companion object {
        const val ARG_USER_ID = "ARG_USER_ID"
    }

}