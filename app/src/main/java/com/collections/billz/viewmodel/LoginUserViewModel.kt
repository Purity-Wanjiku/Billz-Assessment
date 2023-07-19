package com.collections.billz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collections.billz.models.LoginRequest
import com.collections.billz.models.LoginResponse
import com.collections.billz.models.RegisterResponse
import com.collections.billz.repository.LoginUserRepository
import com.collections.billz.repository.UserRepository
import kotlinx.coroutines.launch

class LoginUserViewModel : ViewModel() {
    val errorLiveData = MutableLiveData<String>()
    val loginuserRepository = LoginUserRepository()
    val regLiveData = MutableLiveData<LoginResponse>()
    var regError = MutableLiveData<String>()

    fun loginUser ( loginRequest: LoginRequest){
        viewModelScope.launch{
            var response = loginuserRepository.loginUser(loginRequest)
            if (response.isSuccessful){
                regLiveData.postValue(response.body())
            }
            else{
                errorLiveData.postValue(response.errorBody()?.string())
            }
        }
    }

}