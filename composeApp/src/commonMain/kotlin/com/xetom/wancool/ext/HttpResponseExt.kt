package com.xetom.wancool.ext

import io.ktor.client.statement.*

fun HttpResponse.isOk(): Boolean = this.status.value in 200..299