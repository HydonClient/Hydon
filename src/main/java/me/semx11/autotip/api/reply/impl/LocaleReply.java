package me.semx11.autotip.api.reply.impl;

import com.google.gson.JsonObject;
import me.semx11.autotip.api.RequestType;
import me.semx11.autotip.api.reply.Reply;
import me.semx11.autotip.chat.LocaleHolder;

import java.util.Locale;

public class LocaleReply extends Reply {

    private Locale lang;
    private JsonObject locale;

    public LocaleReply(boolean success) {
        super(success);
    }

    public LocaleHolder getLocaleHolder() {
        return new LocaleHolder(lang, locale);
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.LOCALE;
    }
}
