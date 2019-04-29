package com.ak.be.engine.controller.order.impl

import com.ak.be.engine.EngineApplicationTests
import com.ak.be.engine.controller.order.CREATE_ORDER
import com.ak.be.engine.controller.restaurant.dto.CreateOrderRequest
import com.ak.be.engine.controller.restaurant.dto.OrderDto
import com.fasterxml.jackson.annotation.JsonProperty
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


class OrderControllerDefaultTest : EngineApplicationTests() {

    lateinit var restTemplateWithBasic: TestRestTemplate

    @Before
    fun setUp() {
//        restTemplateWithBasic = testRestTemplate.withBasicAuth("ian", "ian")
        restTemplateWithBasic = testRestTemplate.withBasicAuth("spring-security-oauth2-read-client", "spring-security-oauth2-read-client-password1234")
        val token = getToken()

//        val headers = HttpHeaders()
        val arrayList = ArrayList<ClientHttpRequestInterceptor>()
        arrayList.add(ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.add("Authorization", "Bearer ${token.accessToken}")
            execution.execute(request, body)
        })
        testRestTemplate.restTemplate.interceptors = arrayList
    }

    @Test
    fun createOrder() {

        val result = testRestTemplate.postForEntity(CREATE_ORDER, CreateOrderRequest(arrayOf(1, 2).toList(), null, 1), OrderDto::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result)
        Thread.sleep(2000)
    }


    fun getToken(): GetTokenResponse {

        val headers = HttpHeaders()
        headers.contentType = APPLICATION_FORM_URLENCODED

        val map = LinkedMultiValueMap<String, String>()
        map.add("grant_type", "password")
        map.add("username", "email1@email.com")
        map.add("password", "password")

        val request = HttpEntity<MultiValueMap<String, String>>(map, headers)

        val result = restTemplateWithBasic.postForEntity("/oauth/token", request, GetTokenResponse::class.java)
        Assert.assertNotNull(result)
        Assert.assertEquals(result.statusCode, HttpStatus.OK)
        Assert.assertNotNull(result.body)
        return result.body!!
    }

}

data class GetTokenResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("expires_in") val expiresIn: Int,
        @JsonProperty("scope") val scope: String)
//{"access_token":"250def6b-8d5f-4e0a-a7c2-a340b80de532","token_type":"bearer","refresh_token":"2ccb8b96-45ab-42e0-9e6e-854fe8e06350","expires_in":10791,"scope":"read"}