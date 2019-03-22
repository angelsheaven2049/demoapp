package com.angelsheaven.teremdemoapp.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.angelsheaven.teremdemoapp.databinding.FragmentLoginBinding
import com.angelsheaven.teremdemoapp.utilities.isMyDataItInitialized
import com.angelsheaven.teremdemoapp.utilities.provideLoginFragmentViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private val mViewModel by lazy {
        val factory = context?.let {
            provideLoginFragmentViewModelFactory(it, activity?.isMyDataItInitialized(),this)
        }
        ViewModelProviders.of(this,factory).get(LoginFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.viewmodel = mViewModel

        return binding.root
    }


}
