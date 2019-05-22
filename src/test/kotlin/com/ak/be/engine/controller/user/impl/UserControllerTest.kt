package com.ak.be.engine.controller.user.impl

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.user.CREATE_USER
import com.ak.be.engine.controller.user.GET_ADMIN_ME
import com.ak.be.engine.controller.user.GET_USER_BY_ID
import com.ak.be.engine.controller.user.GET_USER_ME
import com.ak.be.engine.controller.user.dto.*
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus


internal class UserControllerTest : EngineApplicationTests() {

    @Test
    fun getUserById() {
        authenticate()
        val userId = 1

        val tables = testRestTemplate.getForEntity(GET_USER_BY_ID.replace("{userId}", userId.toString()), GetUserByIdResponse::class.java)
        Assert.assertNotNull(tables)
        Assert.assertEquals(tables.statusCode, HttpStatus.OK)
        Assert.assertNotNull(tables.body)
        Assert.assertNotNull(tables.body!!.user)
    }

    @Test
    fun getUserMe() {
        authenticate("email2@email.com", "password")
        val tables = testRestTemplate.getForEntity(GET_USER_ME, GetUserMeResponse::class.java)
        Assert.assertNotNull(tables)
        Assert.assertEquals(tables.statusCode, HttpStatus.OK)
        Assert.assertNotNull(tables.body)
        Assert.assertNotNull(tables.body!!.context)
    }

    @Test
    fun getAdminMe() {
        authenticate()
        val tables = testRestTemplate.getForEntity(GET_ADMIN_ME, GetAdminMeResponse::class.java)
        Assert.assertNotNull(tables)
        Assert.assertEquals(tables.statusCode, HttpStatus.OK)
        Assert.assertNotNull(tables.body)
        Assert.assertNotNull(tables.body!!.context)
    }

    @Test
    fun createUser() {

        val email = "test@email.com"
        val password = "password"
        val createUserRequest = CreateUserRequest(email, "new firstname", "new lastname", password)

        val responseEntity = testRestTemplate.postForEntity(CREATE_USER, createUserRequest, CreateUserResponse::class.java)
        Assert.assertEquals(responseEntity.statusCode, HttpStatus.OK)
        Assert.assertNotNull(responseEntity)
        Assert.assertNotNull(responseEntity.body)
        Assert.assertNotNull(responseEntity.body!!.user.id)


        authenticate(email, password)
    }

}