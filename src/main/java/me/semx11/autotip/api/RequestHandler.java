package me.semx11.autotip.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import me.semx11.autotip.Autotip;
import me.semx11.autotip.api.reply.Reply;
import me.semx11.autotip.api.request.Request;
import me.semx11.autotip.gson.adapter.impl.LocaleAdapter;
import me.semx11.autotip.gson.adapter.impl.PatternAdapter;
import me.semx11.autotip.gson.adapter.impl.SessionKeyAdapter;
import me.semx11.autotip.gson.adapter.impl.VersionAdapter;
import me.semx11.autotip.util.AutoTipVersion;
import me.semx11.autotip.util.ErrorReport;
import net.hydonclient.Hydon;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public class RequestHandler {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Locale.class, new LocaleAdapter())
            .registerTypeAdapter(Pattern.class, new PatternAdapter())
            .registerTypeAdapter(SessionKey.class, new SessionKeyAdapter())
            .registerTypeAdapter(AutoTipVersion.class, new VersionAdapter())
            .create();

    private static Autotip autotip;

    public static void setAutotip(Autotip autotip) {
        RequestHandler.autotip = autotip;
    }

    public static Optional<Reply> getReply(Request request, URI uri) {
        String json = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestProperty("User-Agent", "Autotip v" + autotip.getAutoTipVersion());

            InputStream input;
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
            }
            json = IOUtils.toString(input, StandardCharsets.UTF_8);
            Hydon.LOGGER.info(request.getType() + " JSON: " + json);

            Reply reply = GSON.fromJson(json, (Type) request.getType().getReplyClass());

            return Optional.ofNullable(reply);
        } catch (IOException | JsonParseException e) {
            ErrorReport.reportException(e);
            Hydon.LOGGER.info(request.getType() + " JSON: " + json);
            return Optional.empty();
        }
    }
}
