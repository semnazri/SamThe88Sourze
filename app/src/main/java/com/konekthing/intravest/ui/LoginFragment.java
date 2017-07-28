/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.konekthing.intravest.App;
import com.konekthing.intravest.Interface.UserInterface;
import com.konekthing.intravest.R;
import com.konekthing.intravest.network.BasicAuthInterceptor;
import com.konekthing.intravest.network.POJO.UserPojo;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collections;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    public static final String PREF_IS_LOGIN = "is_login";

    static final String LOGIN_URL = "auth/";

    private PageNavigator mNavigator;
    private View mContentView, mLoadingView;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private Button mBtnLogin, btn_reg;
    private String tag_json_obj = "jobj_req";


    private TextView.OnEditorActionListener mOnEditorAction =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                        try {
                            login(getActivity(), mInputEmail.getText().toString(),
                                    mInputPassword.getText().toString(), getSharedPreferences());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                    return false;
                }
            };

    public LoginFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mNavigator = (PageNavigator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement PageNavigator.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_new, container, false);
//        mContentView = view.findViewById(R.id.content_view);
//        mLoadingView = view.findViewById(R.id.loading_view);

        mInputEmail = (EditText) view.findViewById(R.id.edt_username);
        mInputPassword = (EditText) view.findViewById(R.id.edt_password);
        mInputPassword.setTransformationMethod(new PasswordTransformationMethod());
        mInputPassword.setTypeface(Typeface.DEFAULT);
        mInputPassword.setOnEditorActionListener(mOnEditorAction);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        btn_reg = (Button) view.findViewById(R.id.btn_register);
//        mLoadingView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                crossfadeInverse(mContentView, mLoadingView);
                try {
                    login(getActivity(), mInputEmail.getText().toString(),
                            mInputPassword.getText().toString(), getSharedPreferences());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void setTitle(CharSequence title) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigator = null;
    }

    private void login(final Context context, final String username, final String password, final SharedPreferences sp) throws JSONException, IOException {

//        JacksonRequest<Profile> request = JacksonRequest.<Profile>method(Request.Method.GET)
//                .url(App.BASE_URL + LOGIN_URL )
//                .listener(new Response.Listener<Profile>() {
//                    @Override
//                    public void onResponse(Profile response) {
//
//                        Log.d ("aa",response.toString());
//                        sp.edit()
//                                .putLong(ProfileFragment.PREF_ID, response.getId())
//                                .putString(ProfileFragment.PREF_EMAIL, response.getEmail())
//                                .putString(ProfileFragment.PREF_PASSWORD, password)
//                                .putString(ProfileFragment.PREF_SALUTATION, response.getSalutation())
//                                .putString(ProfileFragment.PREF_FIRSTNAME, response.getFirstname())
//                                .putString(ProfileFragment.PREF_LASTNAME, response.getLastname())
//                                .putString(ProfileFragment.PREF_COMPANY, response.getCompany())
//                                .putString(ProfileFragment.PREF_POSITION, response.getPosition())
//                                .putString(ProfileFragment.PREF_PHONE, response.getPhone())
//                                .putString(ProfileFragment.PREF_PHOTO, response.getPhoto())
//                                .putBoolean(PREF_IS_LOGIN, true)
//                                .apply();
//                        startActivity(new Intent(context, MainActivity.class));
//                        getActivity().finish();
//                    }
//                })
//                .errorListener(new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
////                        crossfade(mContentView, mLoadingView);
//                    }
//                })
//                .clazz(Profile.class)
//                .username(username)
//                .password(password)
//                .build();
//        try {
//            request.getHeaders();
//        } catch (AuthFailureError authFailureError) {
//            authFailureError.printStackTrace();
//        }
//        request.setTag(getTag());


//        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//        okHttpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                okhttp3.Request originalRequest = chain.request();
//
//                String usernameAndPassword = username + ":" + password;
//                byte[] bytes = usernameAndPassword.getBytes("UTF-8");
//                String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
//
//                Request.Builder builder = originalRequest.newBuilder().addHeader("Authorization",
//                        "Basic " + encoded);
//
//                Request newRequest = builder.build();
//                return chain.proceed(newRequest);
//            }
//        });
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor(username, password))
//                .build();
        OkHttpClient client = new OkHttpClient().newBuilder().addNetworkInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        url = url.newBuilder().username(username).password(password).build();
                        Request newRequest = request.newBuilder().url(url).build();
                        return chain.proceed(newRequest);
                    }
                }
        ).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface service = retrofit.create(UserInterface.class);
        Call<ResponseBody> call = service.getAuthorize();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                Toast.makeText(getActivity(), "berhasil", Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                try {
                    UserPojo userpojo = gson.fromJson(response.body().string(), UserPojo.class);
                    Log.d("response", userpojo.toString());
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                android.util.Log.d("onFailure", t.toString());
                Toast.makeText(getContext(), "gagal", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Loading CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        // I'm using Java7. If you used Java6 close it manually with finally.
        try (InputStream cert = context.getResources().openRawResource(R.raw.certificate)) {
            ca = cf.generateCertificate(cert);
        }

        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
}
