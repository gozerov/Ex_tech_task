package ru.gozerov.ex_tech_task.screens.contacts_list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gozerov.ex_tech_task.R
import ru.gozerov.ex_tech_task.databinding.DialogFilterContactsBinding

class FilterContactsDialog: BottomSheetDialogFragment() {

    private lateinit var binding: DialogFilterContactsBinding

    private val filter: String?
        get() = requireArguments().getString(ARG_FILTER)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterContactsBinding.inflate(inflater, container, false)
        filter?.let { f ->
            when (f) {
                getString(R.string.dialog_filter1) -> {
                    binding.radioGroup.check(R.id.dialogRadioButton1)
                }
                getString(R.string.dialog_filter2) -> {
                    binding.radioGroup.check(R.id.dialogRadioButton2)
                }
            }
        }
        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.dialogRadioButton1 -> {
                    requireArguments().putString(ARG_FILTER, getString(R.string.dialog_filter1))
                }
                R.id.dialogRadioButton2 -> {
                    requireArguments().putString(ARG_FILTER, getString(R.string.dialog_filter2))
                }
            }

        }
        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        parentFragmentManager.setFragmentResult(DIALOG_REQUEST_KEY, bundleOf(KEY_FILTER to filter))
        super.onCancel(dialog)
    }


    companion object {
        private const val KEY_FILTER = "KEY_FILTER"
        const val ARG_FILTER = "ARG_FILTER"
        const val DIALOG_REQUEST_KEY = "DIALOG_REQUEST_KEY"

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String?) -> Unit) {
            manager.setFragmentResultListener(DIALOG_REQUEST_KEY, lifecycleOwner) { _, result ->
                listener(result.getString(KEY_FILTER))
            }
        }

    }

}