package me.snipz.cooldowns.builder.general

import me.snipz.cooldowns.api.ICooldown
import org.bukkit.entity.Player
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

open class CooldownBuilder<T : CooldownBuilder<T>> protected constructor() {

    companion object {
        var groupRecognizer = { player: Player -> "default" }
            private set

        fun groupRecognizer(recognizer: (player: Player) -> String) {
            groupRecognizer = recognizer
        }
    }

    protected var key: String? = null
    protected var duration: Duration = 1.seconds

    fun key(key: String): T {
        this.key = key
        return this as T
    }

    fun duration(duration: Duration): T {
        this.duration = duration
        return this as T
    }

    open fun build(): ICooldown {
        return object : ICooldown {
            override fun getKey(): String {
                return key ?: "error"
            }

            override fun getTime(): Duration {
                return duration
            }
        }
    }
}