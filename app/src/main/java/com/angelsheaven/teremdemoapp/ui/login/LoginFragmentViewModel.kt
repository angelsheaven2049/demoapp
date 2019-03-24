package com.angelsheaven.teremdemoapp.ui.login

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.angelsheaven.teremdemoapp.MyApplication
import com.angelsheaven.teremdemoapp.R
import com.angelsheaven.teremdemoapp.data.NewsRepository
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import javax.inject.Inject

class LoginFragmentViewModel(private val mFragment: Fragment?) : ViewModel(), MyLogger {

    @Inject lateinit var repository:NewsRepository
    @Inject lateinit var mContext: Context

    init {
        MyApplication.appComponent.inject(this)
    }

    val username by lazy { ObservableField<String>() }

    val password by lazy { ObservableField<String>() }

    val error by lazy { ObservableField<String>() }

    val textWatcher by lazy {
        object : TextWatcher {
            override fun afterTextChanged(edt: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isPasswordValid(text.toString())) {
                    error.set(mContext?.getString(R.string.error_password))
                } else {
                    error.set(null)
                    if (mFragment?.let { findNavController(it).currentDestination?.id } == R.id.login_dest) {
                        findNavController(mFragment).navigate(R.id.action_loginFragment_to_list_news_dest)
                    }
                }
            }
        }
    }

    fun isPasswordValid(text: String?): Boolean {
        return text != null && text.length >= 8
    }

    fun onLogin() {
        val input = password.get().toString()
        if (!isPasswordValid(input)) {
            error.set(mContext?.getString(R.string.error_password))
        } else {
            error.set(null)
            if (mFragment?.let { findNavController(it).currentDestination?.id } == R.id.login_dest) {
                findNavController(mFragment).navigate(R.id.action_loginFragment_to_list_news_dest)
            }
        }
    }
}

class LoginFragmentViewModelFactory(
    private val mFragment: Fragment
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginFragmentViewModel(mFragment) as T
    }
}