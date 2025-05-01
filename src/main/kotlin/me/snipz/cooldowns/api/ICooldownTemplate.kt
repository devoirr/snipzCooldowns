package me.snipz.cooldowns.api

import org.bukkit.entity.Player

interface ICooldownTemplate : ICooldown {

    fun apply(player: Player): IAppliedCooldown?

}