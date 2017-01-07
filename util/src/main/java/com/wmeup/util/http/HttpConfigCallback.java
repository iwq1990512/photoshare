package com.wmeup.util.http;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 * @author zy
 */
public interface HttpConfigCallback {
    public void callback(final Request request, Form form);
}
