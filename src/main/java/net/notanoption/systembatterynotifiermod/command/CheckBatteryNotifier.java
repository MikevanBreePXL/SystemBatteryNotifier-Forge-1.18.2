package net.notanoption.systembatterynotifiermod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.notanoption.systembatterynotifiermod.procedures.CheckLaptopBatteryChargeProcedure;

public class CheckBatteryNotifier {
    public CheckBatteryNotifier(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Ensure command registration is for server-side
        dispatcher.register(Commands.literal("checkBatteryNotifier")
                .executes(arguments -> {
                    ServerLevel world = arguments.getSource().getLevel();
                    Entity entity = arguments.getSource().getEntity();
                    if (entity == null)
                        entity = FakePlayerFactory.getMinecraft(world);

                    return CheckLaptopBatteryChargeProcedure.checkExecutorService();
                }));
    }
}
