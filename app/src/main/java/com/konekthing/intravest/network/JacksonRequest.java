/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.network;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JacksonRequest<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format(
            "application/json; charset=%s", PROTOCOL_CHARSET);
    private final String mUsername;
    private final String mPassword;
    private final String mRequestKey;
    protected final Listener<T> mListener;
    protected final Class<T> mClazz;
    private final Object mRequestBody;

    JacksonRequest(Builder<T> builder) {
        super(builder.method, builder.url, builder.errorListener);
        mListener = builder.listener;
        mClazz = builder.clazz;
        mUsername = builder.username;
        mPassword = builder.password;
        mRequestBody = builder.body;
        mRequestKey = builder.key;
    }

    public static <T> _Url<T> method(int method) {
        return new Builder<T>(method);
    }

    public interface _Url<T> {
        _Listener<T> url(String url);
    }

    public interface _Listener<T> {
        _ErrorListener<T> listener(Listener<T> listener);
    }

    public interface _ErrorListener<T> {
        _Class<T> errorListener(ErrorListener errorListener);
    }

    public interface _Class<T> {
        _Builder<T> clazz(Class<T> clazz);
    }

    public interface _Builder<T> {
        _Builder<T> username(String username);

        _Builder<T> password(String password);

        _Builder<T> key(String key);

        _Builder<T> body(Object body);

        JacksonRequest<T> build();
    }

    public static class Builder<T> implements _Builder<T>, _Class<T>, _ErrorListener<T>,
            _Listener<T>, _Url<T> {

        private int method;
        private String url;
        private ErrorListener errorListener;
        private Listener<T> listener;
        private Class<T> clazz;
        private String username;
        private String password;
        private String key;
        private Object body;

        Builder(int method) {
            this.method = method;
        }

        @Override
        public _Listener<T> url(String url) {
            this.url = url;
            return this;
        }

        @Override
        public _ErrorListener<T> listener(Listener<T> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public _Class<T> errorListener(ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        @Override
        public _Builder<T> clazz(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        @Override
        public _Builder<T> username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public _Builder<T> password(String password) {
            this.password = password;
            return this;
        }

        @Override
        public _Builder<T> key(String key) {
            this.key = key;
            return this;
        }

        @Override
        public _Builder<T> body(Object body) {
            this.body = body;
            return this;
        }

        @Override
        public JacksonRequest<T> build() {
            return new JacksonRequest<T>(this);
        }

    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T json = JSON.std.beanFrom(mClazz, response.data);
            return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONObjectException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("Parse Error"));
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("IO Error"));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError instanceof NoConnectionError) {
            volleyError.printStackTrace();
            return new VolleyError("Slow or No Connection");
        }
        try {
            if (volleyError.networkResponse != null) {
                ErrorMessage msg = JSON.std.beanFrom(ErrorMessage.class,
                        volleyError.networkResponse.data);
                return new VolleyError(msg.getError());
            }

        } catch (JSONObjectException e) {
            return new VolleyError(e.getClass().getSimpleName());
        } catch (IOException e) {
            return new VolleyError(e.getClass().getSimpleName());
        }
        return new VolleyError(volleyError.getClass().getSimpleName());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return createBasicAuthHeader();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            if (mRequestBody == null) {
                return null;
            } else {
                return JSON.std.asBytes(mRequestBody);
            }
        } catch (JSONObjectException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> createBasicAuthHeader() {
        Map<String, String> headerMap = new HashMap<String, String>();
        if (mUsername != null && mPassword != null) {
            String credentials = mUsername + ":" + mPassword;
            String encodedCredentials = Base64.encodeToString(credentials.getBytes(),
                    Base64.NO_WRAP);
            headerMap.put("Authorization", "Basic " + encodedCredentials);
        }
        headerMap.put("X-API-KEY", mRequestKey);
        return headerMap;
    }

}
