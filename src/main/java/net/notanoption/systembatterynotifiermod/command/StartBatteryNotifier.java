package net.notanoption.systembatterynotifiermod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.notanoption.systembatterynotifiermod.procedures.CheckLaptopBatteryChargeProcedure;

public class StartBatteryNotifier {
    public StartBatteryNotifier(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Ensure command registration is for server-side
        dispatcher.register(Commands.literal("startBatteryNotifier")
                .executes(arguments -> {
                    ServerLevel world = arguments.getSource().getLevel();
                    Entity entity = arguments.getSource().getEntity();
                    if (entity == null)
                        entity = FakePlayerFactory.getMinecraft(world);

                    return CheckLaptopBatteryChargeProcedure.execute();
                }));
    }
}
