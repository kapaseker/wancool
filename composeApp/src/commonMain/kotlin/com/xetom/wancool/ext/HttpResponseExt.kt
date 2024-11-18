package com.xetom.wancool.ext

import io.ktor.client.statement.HttpResponse

fun HttpResponse.isOk(): Boolean = this.status.value in 200..299