package ru.vyapps.vkgram.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.databinding.FragmentLogInBinding

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private val viewModel: LogInViewModel by viewModels()

    private var mutableBinding: FragmentLogInBinding? = null
    private val binding get() = mutableBinding!!

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
            VK.login(requireActivity())
        }
    }
}