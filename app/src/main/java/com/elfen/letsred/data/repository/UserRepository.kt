package com.elfen.letsred.data.repository

import com.elfen.letsred.data.local.dao.UserDao
import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(
    private val apiService: APIService,
    private val userDao: UserDao
) {
    suspend fun fetchUserByUsername(username: String) {
        withContext(Dispatchers.IO){
            val user = apiService.getUserByUsername(username)
            userDao.upsertUser(user.data.asEntity())
        }
    }

    fun userById(id: String) = userDao.getUserById(id).map { it?.asAppModel() }
}