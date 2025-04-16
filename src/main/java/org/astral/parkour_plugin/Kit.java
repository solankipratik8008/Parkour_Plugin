package org.astral.parkour_plugin;

import org.jetbrains.annotations.NotNull;

public final class Kit {

    private Kit() {}

    public static @NotNull org.astral.parkour_plugin.Compatibilizer.Scheduler.Core.RegionScheduler getRegionScheduler() {
        return org.astral.parkour_plugin.Compatibilizer.Scheduler.Core.RegionScheduler.__API();
    }

    public static @NotNull org.astral.parkour_plugin.Compatibilizer.Scheduler.Core.AsyncScheduler getAsyncScheduler() {
        return org.astral.parkour_plugin.Compatibilizer.Scheduler.Core.AsyncScheduler.__API();
    }
}