package net.lukkit.lukkit.sandbox.globals.http;

import net.lukkit.lukkit.Constants;

import java.util.LinkedList;

public class BaseRequest {
    class Header {
        String key;
        String value;

        Header(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final String DEFAULT_USER_AGENT = Constants.formatTokens("Lukkit/@@version@@");

    protected final LinkedList<Header> headers = new LinkedList<>();
    protected String contentType = null;
    protected String requestBody = null;
    protected String userAgent = DEFAULT_USER_AGENT;

    public BaseRequest withHeader(String name, String content) {
        // Append to list as duplicate headers are allowed
        // (see https://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2)
        // TODO: check if header is able to be duplicated, possible hashmap
        this.headers.addLast(new Header(name, content));
        return this;
    }

    public BaseRequest withContentType(String type) {
        type = type.toLowerCase();

        if (!type.matches("(application/json|text/plain)")) {
            // TODO: logging
            System.out.println("An incompatible content type was passed to buildRequest():withContentType()");
            return null;
        }

        contentType = type;
        return this;
    }

    public BaseRequest withUserAgent(String ua) {
        if (!ua.matches("\\w/[0-9|.]+ .*")) {
            // TODO: logging
            System.out.println("Bad User Agent provided to buildRequest():withUserAgent(). " +
                    "Defaulting to " + userAgent);
        } else {
            userAgent = ua;
        }

        return this;
    }

    public BaseRequest withBody(String text) {
        // TODO checks, escaping
        requestBody = text;
        return this;
    }

    public BaseRequest withBody(Object json) {
        // TODO: add json lib
        requestBody = json.toString();
        return this;
    }
}
