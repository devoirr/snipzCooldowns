package me.snipz.cooldowns.api

import kotlin.time.Duration

interface ICooldown {

    fun getKey(): String
    fun getTime(): Duration

}