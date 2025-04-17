package me.snipz.cooldowns

import me.snipz.cooldowns.api.ICooldownTemplate
import me.snipz.cooldowns.api.service.ICooldownService
import org.bukkit.entity.Player
import kotlin.time.Duration

class CooldownServiceImpl(private val storage: CooldownsStorage) : ICooldownService {

    override fun registerCooldown(cooldown: ICooldownTemplate) {
        storage.registerCooldown(cooldown)
    }

    override fun hasPlayerCooldown(player: Player, key: String): Boolean {
        return storage.hasPlayerCooldown(player, key)
    }

    override fun applyCooldown(player: Player, key: String) {
        storage.applyCooldown(player, key)
    }

    override fun getLeftCooldown(player: Player, key: String): Duration {
        return storage.getLeftCooldown(player, key)
    }
}