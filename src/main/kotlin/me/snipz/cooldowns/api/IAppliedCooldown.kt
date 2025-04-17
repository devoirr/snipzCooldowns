package me.snipz.cooldowns.api

import kotlin.time.Duration

interface IAppliedCooldown : ICooldown {

    fun getPlayer(): String
    fun getLeft(): Duration
    fun hasExpired(): Boolean

}