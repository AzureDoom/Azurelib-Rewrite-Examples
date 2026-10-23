package mod.azure.azexamples.profiler;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;

import mod.azure.azurelib.AzureLib;

@SideOnly(Side.CLIENT)
public final class AzProfilerCommand extends CommandBase {

    private static final int SUMMARY_ROWS = 8;

    private static final List<String> SUBCOMMANDS = Arrays.asList("start", "stop", "reset", "status", "heap");

    @Override
    public String getName() {
        return "azprofile";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/azprofile <start [jfr|leaks] | stop [label] | reset | status | heap>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length == 0) {
            throw new WrongUsageException(getUsage(sender));
        }

        String sub = args[0].toLowerCase(Locale.ROOT);

        if ("start".equals(sub)) {
            String mode = args.length > 1 ? args[1].toLowerCase(Locale.ROOT) : "";
            start(sender, "jfr".equals(mode) || "leaks".equals(mode), "leaks".equals(mode));
        } else if ("stop".equals(sub)) {
            stop(sender, args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : null);
        } else if ("reset".equals(sub)) {
            reset(sender);
        } else if ("status".equals(sub)) {
            status(sender);
        } else if ("heap".equals(sub)) {
            heap(sender);
        } else {
            throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public List<String> getTabCompletions(
        MinecraftServer server,
        ICommandSender sender,
        String[] args,
        @Nullable BlockPos targetPos
    ) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, SUBCOMMANDS);
        }

        if (args.length == 2 && "start".equalsIgnoreCase(args[0])) {
            return getListOfStringsMatchingLastWord(args, "jfr", "leaks");
        }

        return Collections.emptyList();
    }

    private static void say(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString(message));
    }

    private static void start(ICommandSender sender, boolean withJfr, boolean leaks) {
        if (!ProfilerControl.start()) {
            say(sender, "AzureLib profiler is already running.");
            return;
        }

        if (withJfr) {
            try {
                JfrRecorder.start(leaks);
            } catch (Exception e) {
                AzureLib.LOGGER.warn("Could not start JFR recording", e);
                say(sender, "JFR unavailable (" + rootMessage(e) + "), timing only.");
            }
        }

        String suffix = JfrRecorder.isLeakMode()
            ? " with JFR leak sampling."
            : JfrRecorder.isRecording()
                ? " with JFR."
                : ".";
        say(sender, "AzureLib profiler started" + suffix);
    }

    private static void stop(ICommandSender sender, String label) {
        try {
            ProfilerControl.Result result = ProfilerControl.stop(label);

            if (result == null) {
                say(sender, "AzureLib profiler is not running.");
                return;
            }

            ProfilerSession session = result.session();
            say(
                sender,
                String.format(Locale.ROOT, "Profiled %.1f s. Top stages by self time:", session.wallNanos() / 1e9)
            );

            for (ReportRow row : result.rows().subList(0, Math.min(SUMMARY_ROWS, result.rows().size()))) {
                say(
                    sender,
                    String.format(
                        Locale.ROOT,
                        "  %5.2f%%  %s  %s  (p99 %.0f us)",
                        ReportWriter.threadPercent(row, session),
                        row.stage(),
                        row.subject(),
                        row.p99Nanos() / 1e3
                    )
                );
            }

            say(sender, "Report: " + result.report());

            if (result.jfr() != null) {
                say(sender, "JFR: " + result.jfr());
            }
        } catch (Exception e) {
            AzureLib.LOGGER.error("Failed to write AzureLib profiler report", e);
            say(sender, "Failed to write report: " + rootMessage(e));
        }
    }

    private static void reset(ICommandSender sender) {
        if (!ProfilerControl.isRunning()) {
            say(sender, "AzureLib profiler is not running.");
            return;
        }

        ProfilerControl.reset();
        say(sender, "AzureLib profiler data reset.");
    }

    private static void status(ICommandSender sender) {
        ProfilerSession session = ProfilerControl.session();

        if (session == null) {
            say(sender, "AzureLib profiler is not running.");
            return;
        }

        say(
            sender,
            String.format(
                Locale.ROOT,
                "Running %.1f s, %d entries, %d unbalanced%s",
                session.wallNanos() / 1e9,
                session.entryCount(),
                session.unbalanced(),
                JfrRecorder.isLeakMode() ? ", JFR leak sampling on" : JfrRecorder.isRecording() ? ", JFR on" : ""
            )
        );
    }

    private static void heap(ICommandSender sender) {
        Runtime runtime = Runtime.getRuntime();
        long before = runtime.totalMemory() - runtime.freeMemory();
        System.gc();
        long after = runtime.totalMemory() - runtime.freeMemory();

        say(
            sender,
            String.format(
                Locale.ROOT,
                "Heap: %d MB used after GC (%d MB before), %d MB committed, %d MB max",
                after >> 20,
                before >> 20,
                runtime.totalMemory() >> 20,
                runtime.maxMemory() >> 20
            )
        );

        World world = Minecraft.getMinecraft().world;

        if (world != null) {
            List<Entity> entities = new ArrayList<>(world.loadedEntityList);
            say(
                sender,
                String.format(
                    Locale.ROOT,
                    "Client world: %d entities, %d tile entities",
                    entities.size(),
                    world.loadedTileEntityList.size()
                )
            );
        }

        AzureLib.LOGGER.info(
            "AzureLib heap check: {} MB used after GC, {} MB committed, {} MB max",
            after >> 20,
            runtime.totalMemory() >> 20,
            runtime.maxMemory() >> 20
        );
    }

    private static String rootMessage(Throwable e) {
        Throwable root = e;

        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }

        return root.getMessage() != null ? root.getMessage() : root.getClass().getSimpleName();
    }
}
