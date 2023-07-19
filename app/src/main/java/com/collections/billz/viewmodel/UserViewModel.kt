package com.collections.billz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collections.billz.models.RegisterRequest
import com.collections.billz.models.RegisterResponse
import com.collections.billz.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
// val regLiveData = mutableListOf<RegisterResponse>()
    val errorLiveData = MutableLiveData<String>()
    val userRepository = UserRepository()
    val regLiveData = MutableLiveData<RegisterResponse>()
    var regError = MutableLiveData<String>()

    fun registerUser ( registerRequest: RegisterRequest){
        viewModelScope.launch{
            var response = userRepository.registerUser(registerRequest)
            if (response.isSuccessful){
                regLiveData.postValue(response.body())
            }
            else{
                errorLiveData.postValue(response.errorBody()?.string())
            }
        }
    }

}