package me.snipz.cooldowns.api.service

import me.snipz.cooldowns.api.ICooldownTemplate
import org.bukkit.entity.Player
import kotlin.time.Duration

interface ICooldownService {

    fun registerCooldown(cooldown: ICooldownTemplate)

    fun hasPlayerCooldown(player: Player, key: String): Boolean
    fun applyCooldown(player: Player, key: String)

    fun getLeftCooldown(player: Player, key: String): Duration
    
}