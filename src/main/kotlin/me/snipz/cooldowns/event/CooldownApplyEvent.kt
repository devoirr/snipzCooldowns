package me.snipz.cooldowns.event

import me.snipz.cooldowns.api.ICooldown
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class CooldownApplyEvent(private val player: Player, cooldown: ICooldown) : Event(), Cancellable {

    private var cancel = false

    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(p0: Boolean) {
        this.cancel = p0
    }
}