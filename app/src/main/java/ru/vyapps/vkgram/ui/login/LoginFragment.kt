package ru.vyapps.vkgram.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ViewModelFactory
import ru.vyapps.vkgram.databinding.FragmentLogInBinding
import ru.vyapps.vkgram.utils.applicationComponent
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_log_in) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    private var mutableBinding: FragmentLogInBinding? = null
    private val binding get() = mutableBinding!!

    override fun onAttach(context: Context) {
        context.applicationComponent.loginComponent
            .activity(requireActivity())
            .build()
            .inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mutableBinding = FragmentLogInBinding.bind(view)
        setupLoginTextField()
        setupPasswordTextField()
        setupLogInButton()
        setupLogInWithVKButton()
    }

    override fun onDestroy() {
        super.onDestroy()

        mutableBinding = null
    }

    private fun setupLoginTextField() {
        binding.loginTextField.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.login = text.toString()
            viewModel.checkData()
        }
    }

    private fun setupPasswordTextField() {
        binding.passwordTextField.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.password = text.toString()
            viewModel.checkData()
        }
    }

    private fun setupLogInButton() {
        with (binding.logInButton) {
            viewModel.logInButtonEnabled.observe(viewLifecycleOwner, { value ->
                isEnabled = value
            })
        }
    }

    private fun setupLogInWithVKButton() {
        binding.logInWithVkButton.setOnClickListener {
            viewModel.loginWithVk()
        }
    }
}