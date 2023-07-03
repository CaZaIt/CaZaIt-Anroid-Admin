package org.cazait.presentation.ui.signup

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.domain.model.SignUpInfo
import org.bmsk.domain.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    val emailText = MutableStateFlow("")
    val passwordText = MutableStateFlow("")
    val passwordDoubleCheckText = MutableStateFlow("")
    val nicknameText = MutableStateFlow("")
    private val _signUpInfoStateFlow = MutableStateFlow<SignUpInfo?>(null)
    val signUpInfoStateFlow = _signUpInfoStateFlow.asStateFlow()

    fun signUp() {
        viewModelScope.launch {
            val email = emailText.value
            val password = passwordText.value
            val nickname = nicknameText.value

            Log.e("SignUpViewModel", "email=${email}, password=${password}")

            _signUpInfoStateFlow.value = userUseCase.signUp(
                email = email,
                password = password,
                nickname = nickname
            ).first()
        }
    }
}