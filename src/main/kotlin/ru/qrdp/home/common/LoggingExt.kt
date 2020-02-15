package ru.qrdp.home.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun getLogger(clazz: Class<*>): Logger = LoggerFactory.getLogger(clazz)

