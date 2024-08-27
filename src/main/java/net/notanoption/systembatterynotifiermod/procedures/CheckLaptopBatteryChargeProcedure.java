package net.notanoption.systembatterynotifiermod.procedures;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CheckLaptopBatteryChargeProcedure {
    private static final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1);

    public static int execute() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        Runnable task = () -> {
            try {
                ProcessBuilder ps = new ProcessBuilder("powershell", "-command", "(Get-CimInstance Win32_Battery).EstimatedChargeRemaining");
                ps.redirectErrorStream(true);

                Process pr = ps.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String batteryPercentage = in.readLine();

                if (server != null && batteryPercentage != null) {
                    int percent = Integer.parseInt(batteryPercentage.trim());
                    if (percent < 33) {
                        server.getPlayerList().broadcastMessage(new TextComponent("§4========================"), ChatType.SYSTEM, Util.NIL_UUID);
                        server.getPlayerList().broadcastMessage(new TextComponent("§6Server battery level low"), ChatType.SYSTEM, Util.NIL_UUID);
                        server.getPlayerList().broadcastMessage(new TextComponent("§6- §4" + percent + "% §6charge left"), ChatType.SYSTEM, Util.NIL_UUID);
                        server.getPlayerList().broadcastMessage(new TextComponent("§4========================"), ChatType.SYSTEM, Util.NIL_UUID);
                    }
                }
                pr.waitFor();
                if (server != null)
                    server.getPlayerList().broadcastMessage(new TextComponent("---------- Done ----------"), ChatType.SYSTEM, Util.NIL_UUID);
                in.close();
            } catch (Exception e) {
                if (server != null)
                    server.getPlayerList().broadcastMessage(new TextComponent("§4Interrupted!"), ChatType.SYSTEM, Util.NIL_UUID);
            }
        };
        executorService.scheduleAtFixedRate(task, 0, 20, TimeUnit.MINUTES);
        return 1;
    }

    public static void stopExecutorService() {
        executorService.shutdown();
    }
}