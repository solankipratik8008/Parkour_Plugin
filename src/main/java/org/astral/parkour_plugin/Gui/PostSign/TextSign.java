package org.astral.parkour_plugin.Gui.PostSign;

import org.astral.parkour_plugin.Compatibilizer.Adapters.MaterialApi;
import org.astral.parkour_plugin.Compatibilizer.ApiCompatibility;
import org.astral.parkour_plugin.Config.Cache.BlockCache;
import org.astral.parkour_plugin.Gui.Gui;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

public final class TextSign implements TextSignApi, Listener {
    @Override
    public void AddNewMap(final Player player) {
        if (ApiCompatibility.HAS_OPEN_SIGN()) {
            final Block block = player.getWorld().getBlockAt(player.getLocation().add(0, 1, 0));
            block.setType(MaterialApi.getMaterial("OAK_SIGN","SIGN_POST","SIGN"));
            final Sign sign = (Sign) block.getState();
            sign.setLine(0, Title);
            for (int i = 0; i < Lore.size() && i < 3; i++) sign.setLine(i + 1, Lore.get(i));
            Gui.tempBlock.put(player, block);
            BlockCache.createOrUpdateOneBlockCache(player.getUniqueId(), block.getType(), block.getLocation());
            sign.update();
            try {
                @SuppressWarnings("JavaReflectionMemberAccess") Method openSignMethod = Player.class.getMethod("openSign", Sign.class);
                openSignMethod.invoke(player, sign);
            } catch (Exception e) {
                player.sendMessage("Error al abrir el cartel: " + e.getMessage());
            }

        }
    }
}