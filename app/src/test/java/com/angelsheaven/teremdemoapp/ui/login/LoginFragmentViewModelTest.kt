package com.angelsheaven.teremdemoapp.ui.login

import android.content.Context
import com.angelsheaven.teremdemoapp.data.NewsRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class LoginFragmentViewModelTest {

    private var viewModel:LoginFragmentViewModel? = null

    @Mock
    private var repository:NewsRepository? = null

    @Mock var context: Context? = null

    @Mock var mFragmentLoginFragment:LoginFragment? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = Mockito
            .spy(LoginFragmentViewModel
                (context,repository,mFragmentLoginFragment))

    }

    @After
    fun tearDown() {
    }

    @Test
    fun isPasswordValid() {
        val passwordTestValue1 = "Quan1234"
        val passwordTestValue2 = null
        val passwordTestValue3 = ""
        val passwordTestValue4 = "Quan123"

        assertThat(viewModel?.isPasswordValid(passwordTestValue1)
            ,equalTo(true))

    }
}