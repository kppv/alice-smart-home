package ru.qrdp.home.common

import com.fasterxml.jackson.databind.ObjectMapper
import ru.qrdp.home.dto.HomeHeaders
import javax.servlet.http.HttpServletRequest

fun HttpServletRequest.toHomeHeaders(): HomeHeaders =
        HomeHeaders(this.getHeader("Authorization"), this.getHeader("X-Request-Id"))

fun <E> E.toJson(): String = ObjectMapper().writeValueAsString(this)

