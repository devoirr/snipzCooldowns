package me.snipz.cooldowns

import me.snipz.cooldowns.api.IAppliedCooldown
import me.snipz.cooldowns.api.ICooldownTemplate
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.time.Duration

/**
 * Only allow modifying cooldowns in primary thread to avoid sync problems.
 */
class CooldownsStorage(private val plugin: Plugin) {

    private val list = mutableListOf<IAppliedCooldown>()
    private val cooldowns = mutableMapOf<String, ICooldownTemplate>()

    private var currentTask: BukkitTask? = null

    fun startCleaning() {
        if (currentTask != null)
            return

        /* Removed expired cooldowns from the list... */
        currentTask = object : BukkitRunnable() {
            override fun run() {
                list.removeAll { it.hasExpired() }
            }
        }.runTaskTimer(plugin, 0L, 20L * 60 * 3)
    }

    fun stopCleaning() {
        currentTask?.cancel()
        currentTask = null
    }

    fun hasPlayerCooldown(player: Player, key: String): Boolean {
        if (!Bukkit.isPrimaryThread())
            throw IllegalStateException("Cooldowns can only be used in main thread...")

        return list.any {
            it.getPlayer() == player.name
                    && it.getKey() == key
                    && !it.hasExpired()
        }
    }

    fun applyCooldown(player: Player, key: String) {
        if (!Bukkit.isPrimaryThread())
            throw IllegalStateException("Cooldowns can only be used in main thread...")

        cooldowns[key]?.let { cooldown ->
            val applied = cooldown.apply(player)
            if (applied != null)
                list.add(applied)
        }
    }

    fun getLeftCooldown(player: Player, key: String): Duration {
        if (!Bukkit.isPrimaryThread())
            throw IllegalStateException("Cooldowns can only be used in main thread...")

        val active = list.filter { it.getKey() == key && it.getPlayer() == player.name && !it.hasExpired() }
        val first = active.firstOrNull() ?: return Duration.ZERO

        return first.getLeft()
    }

    fun registerCooldown(cooldown: ICooldownTemplate) {
        if (!Bukkit.isPrimaryThread())
            throw IllegalStateException("Cooldowns can only be used in main thread...")

        cooldowns[cooldown.getKey()] = cooldown
    }

    fun getCooldown(key: String): ICooldownTemplate? {
        if (!Bukkit.isPrimaryThread())
            throw IllegalStateException("Cooldowns can only be used in main thread...")

        return cooldowns[key]
    }

}