package net.notanoption.systembatterynotifiermod.events;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.notanoption.systembatterynotifiermod.command.StartBatteryNotifier;
import net.notanoption.systembatterynotifiermod.procedures.CheckLaptopBatteryChargeProcedure;

@Mod.EventBusSubscriber
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new StartBatteryNotifier(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CheckLaptopBatteryChargeProcedure.stopExecutorService();
    }
}
