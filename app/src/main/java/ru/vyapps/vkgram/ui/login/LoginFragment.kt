package ru.vyapps.vkgram.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ViewModelFactory
import ru.vyapps.vkgram.databinding.FragmentLoginBinding
import ru.vyapps.vkgram.utils.applicationComponent
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        context.applicationComponent.loginComponent
            .activity(requireActivity())
            .build()
            .inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)
        setupLoginButton()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
    }
}