package me.snipz.cooldowns

import me.snipz.cooldowns.api.service.ICooldownService
import me.snipz.cooldowns.builder.general.CooldownBuilder
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

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

        val storage = CooldownsStorage(plugin)
        storage.startCleaning()

        plugin.server.servicesManager.register(
            ICooldownService::class.java,
            CooldownServiceImpl(storage),
            plugin,
            ServicePriority.High
        )

        initialized = true
    }

}