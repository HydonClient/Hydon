package net.hydonclient.mixins.crash;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mixin(value = CrashReport.class, priority = 500)
public abstract class MixinCrashReport {

    @Shadow
    @Final
    private String description;

    @Shadow
    @Final
    private Throwable cause;

    @Shadow
    @Final
    private List<CrashReportCategory> crashReportSections;

    @Shadow
    @Final
    private CrashReportCategory theReportCategory;

    /**
     * @author Runemoro
     * @reason Report formatting improvement
     */
    @Overwrite
    public String getCompleteReport() {
        StringBuilder builder = new StringBuilder();

        builder.append("---- Minecraft Crash Report ----\n")
                .append("\n\n")
                .append("Time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date())).append("\n")
                .append("Description: ").append(description)
                .append("\n\n")
                .append(stacktraceToString(cause).replace("\t", "    "))
                .append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for (int i = 0; i < 87; i++) {
            builder.append("-");
        }

        builder.append("\n\n");
        getSectionsInStringBuilder(builder);
        return builder.toString().replace("\t", "    ");
    }

    private static String stacktraceToString(Throwable cause) {
        StringWriter writer = new StringWriter();
        cause.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    /**
     * @author Runemoro
     * @reason Report formatting improvement
     */
    @Overwrite
    public void getSectionsInStringBuilder(StringBuilder builder) {
        for (CrashReportCategory crashreportcategory : crashReportSections) {
            crashreportcategory.appendToStringBuilder(builder);
            builder.append("\n");
        }

        theReportCategory.appendToStringBuilder(builder);
    }
}
