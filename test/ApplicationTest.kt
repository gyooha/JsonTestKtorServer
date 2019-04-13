package io.seroo

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.seroo.model.Product
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    val gson by lazy {
        GsonBuilder()
            .create()
    }

    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun testMyJson() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/my-json").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val type = TypeToken.getParameterized(List::class.java, Product::class.java)
                (gson.getAdapter(type).fromJson(response.content) as? List<Product>)?.apply {
                    assertEquals(100_000, size)
                }
            }
        }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun testMyJsonEmpty() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/my-json-empty").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val type = TypeToken.getParameterized(List::class.java, Product::class.java)
                (gson.getAdapter(type).fromJson(response.content) as? List<Product>)?.apply {
                    assertEquals(100_000, size)
                }
            }
        }
    }
}
