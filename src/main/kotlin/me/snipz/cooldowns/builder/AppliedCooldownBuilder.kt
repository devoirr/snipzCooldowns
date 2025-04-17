package me.snipz.cooldowns.builder

import me.snipz.cooldowns.api.IAppliedCooldown
import me.snipz.cooldowns.builder.general.CooldownBuilder
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class AppliedCooldownBuilder : CooldownBuilder<AppliedCooldownBuilder>() {

    private var player: String? = null
    private var expires: Long? = null

    fun player(player: String): AppliedCooldownBuilder {
        this.player = player
        return this
    }

    fun expires(expires: Long): AppliedCooldownBuilder {
        this.expires = expires
        return this
    }

    override fun build(): IAppliedCooldown {
        return object : IAppliedCooldown {
            override fun getPlayer(): String {
                return player ?: "error"
            }

            override fun getLeft(): Duration {
                val current = System.currentTimeMillis()
                val left = (expires ?: current) - current

                return if (left > 0) {
                    left.toDuration(DurationUnit.MILLISECONDS)
                } else {
                    Duration.ZERO
                }
            }

            override fun hasExpired(): Boolean {
                return (expires ?: 0) < System.currentTimeMillis()
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