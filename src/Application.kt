package io.seroo

import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.seroo.model.Product
import io.seroo.model.ProductFactory
import io.seroo.model.ProductGroup
import io.seroo.model.enum.ProductGroupType
import kotlinx.coroutines.launch
import kotlinx.css.CSSBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowOrMetaDataContent
import kotlinx.html.style

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/my-json") {
            launch {
                val productList = mutableListOf<Product>()
                repeat(100_000) {
                    Product(
                        "0",
                        0,
                        "test",
                        ProductFactory("0", "testFactory"),
                        ProductGroup("0", "testGroup", ProductGroupType.NONE)
                    ).also { productList.add(it) }
                }

                call.respond(productList)
            }
        }

        get("/my-json-empty") {
            launch {
                val productList = mutableListOf<Product>()
                repeat(100_000) {
                    productList.add(Product())
                }

                call.respond(productList)
            }
        }
    }
}

/*@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(Apache)

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }

        get("/styles.css") {
            call.respondCss {
                body {
                    backgroundColor = Color.red
                }
                p {
                    fontSize = 2.em
                }
                rule("p.myclass") {
                    color = Color.blue
                }
            }
        }
    }
}*/

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
