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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Profile;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonRequest;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    public static final String PREF_IS_LOGIN = "is_login";

    static final String LOGIN_URL = "/auth";

    private PageNavigator mNavigator;
    private View mContentView, mLoadingView;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private Button mBtnLogin, btn_reg;

    private TextView.OnEditorActionListener mOnEditorAction =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                        login(getActivity(), mInputEmail.getText().toString(),
                                mInputPassword.getText().toString(), getSharedPreferences());

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
                login(getActivity(), mInputEmail.getText().toString(),
                        mInputPassword.getText().toString(), getSharedPreferences());
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

    private void login(final Context context, String username, final String password, final SharedPreferences sp) {
        JacksonRequest<Profile> request = JacksonRequest.<Profile>method(Request.Method.GET)
                .url(App.BASE_URL + LOGIN_URL )
                .listener(new Response.Listener<Profile>() {
                    @Override
                    public void onResponse(Profile response) {

                        Log.d ("aa",response.toString());
                        sp.edit()
                                .putLong(ProfileFragment.PREF_ID, response.getId())
                                .putString(ProfileFragment.PREF_EMAIL, response.getEmail())
                                .putString(ProfileFragment.PREF_PASSWORD, password)
                                .putString(ProfileFragment.PREF_SALUTATION, response.getSalutation())
                                .putString(ProfileFragment.PREF_FIRSTNAME, response.getFirstname())
                                .putString(ProfileFragment.PREF_LASTNAME, response.getLastname())
                                .putString(ProfileFragment.PREF_COMPANY, response.getCompany())
                                .putString(ProfileFragment.PREF_POSITION, response.getPosition())
                                .putString(ProfileFragment.PREF_PHONE, response.getPhone())
                                .putString(ProfileFragment.PREF_PHOTO, response.getPhoto())
                                .putBoolean(PREF_IS_LOGIN, true)
                                .apply();
                        startActivity(new Intent(context, MainActivity.class));
                        getActivity().finish();
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
//                        crossfade(mContentView, mLoadingView);
                    }
                })
                .clazz(Profile.class)
                .username(username)
                .password(password)
                .build();
        request.setTag(getTag());
        Connection.getInstance(context).addToRequestQueue(request);
    }
}
