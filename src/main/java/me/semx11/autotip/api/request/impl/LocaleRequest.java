package me.semx11.autotip.api.request.impl;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.api.GetBuilder;
import me.semx11.autotip.api.RequestHandler;
import me.semx11.autotip.api.RequestType;
import me.semx11.autotip.api.reply.Reply;
import me.semx11.autotip.api.reply.impl.LocaleReply;
import me.semx11.autotip.api.request.Request;
import me.semx11.autotip.util.AutoTipVersion;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.Locale;
import java.util.Optional;

public class LocaleRequest implements Request<LocaleReply> {

    private final Locale locale;
    private final AutoTipVersion autoTipVersion;

    private LocaleRequest(Autotip autotip) {
        this.locale = autotip.getConfig().getLocale();
        this.autoTipVersion = autotip.getAutoTipVersion();
    }

    public static LocaleRequest of(Autotip autotip) {
        return new LocaleRequest(autotip);
    }

    @Override
    public LocaleReply execute() {
        HttpUriRequest request = GetBuilder.of(this)
                .addParameter("lang", this.locale.toLanguageTag())
                .addParameter("v", this.autoTipVersion.get())
                .build();

        Optional<Reply> optional = RequestHandler.getReply(this, request.getURI());
        return optional
                .map(reply -> (LocaleReply) reply)
                .orElseGet(() -> new LocaleReply(false));
    }

    @Override
    public RequestType getType() {
        return RequestType.LOCALE;
    }
}
