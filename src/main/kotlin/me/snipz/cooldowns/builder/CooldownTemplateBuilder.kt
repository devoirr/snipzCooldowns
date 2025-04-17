package me.snipz.cooldowns.builder

import me.snipz.cooldowns.api.IAppliedCooldown
import me.snipz.cooldowns.api.ICooldownTemplate
import me.snipz.cooldowns.builder.general.CooldownBuilder
import org.bukkit.entity.Player
import kotlin.time.Duration

class CooldownTemplateBuilder : CooldownBuilder<CooldownTemplateBuilder>() {

    private val groups = mutableMapOf<String, Duration>()

    fun group(name: String, duration: Duration): CooldownTemplateBuilder {
        groups[name] = duration
        return this
    }

    override fun build(): ICooldownTemplate {
        return object : ICooldownTemplate {
            override fun apply(player: Player): IAppliedCooldown {
                val playerGroup = CooldownBuilder.groupRecognizer(player)
                val time = groups[playerGroup] ?: duration

                val expires = System.currentTimeMillis() + time.inWholeMilliseconds
                return AppliedCooldownBuilder()
                    .key(key ?: "error")
                    .duration(duration)
                    .expires(expires)
                    .player(player.name)
                    .build()
            }

            override fun getKey(): String {
                return key ?: "error"
            }

            override fun getTime(): Duration {
                return duration
            }
        }
    }
}