package me.snipz.cooldowns

import me.snipz.cooldowns.builder.CooldownTemplateBuilder
import me.snipz.cooldowns.builder.general.CooldownBuilder
import net.kyori.adventure.text.Component
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object CooldownsLib {

    private var initialized = false

    fun initialize(plugin: JavaPlugin) {
        if (initialized)
            return

        if (!plugin.server.pluginManager.isPluginEnabled("Vault")) {
            plugin.logger.info("Vault not found, disabling group-cooldowns...")
        } else {
            val permission = plugin.server.servicesManager.getRegistration(Permission::class.java)!!.provider

            CooldownBuilder.groupRecognizer { player ->
                return@groupRecognizer permission.getPrimaryGroup(player)
            }
        }

        val player: Player = Bukkit.getPlayer("")!!

        val cooldown = CooldownTemplateBuilder()
            .key("feed_cooldown_self")
            .duration(1.minutes)
            .group("vip", 30.seconds)
            .group("premium", 15.seconds)
            .build()

        initialized = true
    }

    fun String.toComponent() = Component.text(this)
}