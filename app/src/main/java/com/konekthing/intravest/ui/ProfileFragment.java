/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.Company;
import com.konekthing.intravest.model.Gallery;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.network.JacksonRequest;
import com.konekthing.intravest.ui.adapters.EditableGalleryAdapter;
import com.konekthing.intravest.ui.widget.DividerItemDecoration;
import com.konekthing.intravest.ui.widget.NotifyingScrollView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link com.konekthing.intravest.ui.BaseFragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    public static final String PREF_ID = "profile_id";
    public static final String PREF_EMAIL = "profile_email";
    public static final String PREF_PASSWORD = "profile_password";
    public static final String PREF_SALUTATION = "profile_salutation";
    public static final String PREF_FIRSTNAME = "profile_firstname";
    public static final String PREF_LASTNAME = "profile_lastname";
    public static final String PREF_COMPANY = "profile_company";
    public static final String PREF_POSITION = "profile_position";
    public static final String PREF_PHONE = "profile_phone";
    public static final String PREF_PHOTO = "profile_photo";

    private  static final String URL_SAVE_PROFILE = "/update_profile/";
    private static final int SELECT_IMAGE = 100;

    private View mHeaderView, mLoadingView;
    private NotifyingScrollView mScrollView;
    private Spinner mSelectSalutation, mSelectProvince, mSelectCity,
            mSelectIndustryLvl1, mSelectIndustryLvl2, mSelectIndustryLvl3, mSelectIndustryLvl4;
    private EditText mInputFirstname, mInputLastname, mInputPosition, mInputCompany, mInputEmailUser, mInputPhoneUser,
            mInputRegisteredCompany, mInputAddress, mInputPostalCode, mInputWebsite, mInputProductService, mInputYearEstablished,
            mInputYearlyTurnOver, mInputTotalEmployee, mInputCapabilities, mInputHistory, mInputPhone, mInputEmail;
    private RecyclerView mRecyclerView;
    private EditableGalleryAdapter mAdapterGallery;
    private Company mCompany;
    private ArrayList<String> mSalutations, mProvinces, mCities, mIndustriesLvl1, mIndustriesLvl2, mIndustriesLvl3, mIndustriesLvl4;
    private int mSelectedIndexGallery;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterGallery = new EditableGalleryAdapter(getActivity(), null, mBrowseClickListener, mUploadClickListener, mDeleteClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sp = getSharedPreferences();
        mHeaderView = view.findViewById(R.id.header_view);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scroll_view);
        mLoadingView = view.findViewById(R.id.loading_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mSelectSalutation = (Spinner) view.findViewById(R.id.select_salutation);
        mSelectProvince = (Spinner) view.findViewById(R.id.select_province);
        mSelectCity = (Spinner) view.findViewById(R.id.select_city);
        mSelectIndustryLvl1 = (Spinner) view.findViewById(R.id.select_industry_lvl1);
        mSelectIndustryLvl2 = (Spinner) view.findViewById(R.id.select_industry_lvl2);
        mSelectIndustryLvl3 = (Spinner) view.findViewById(R.id.select_industry_lvl3);
        mSelectIndustryLvl4 = (Spinner) view.findViewById(R.id.select_industry_lvl4);

        mInputFirstname = (EditText) view.findViewById(R.id.input_firstname);
        mInputLastname = (EditText) view.findViewById(R.id.input_lastname);
        mInputCompany = (EditText) view.findViewById(R.id.input_company);
        mInputPosition = (EditText) view.findViewById(R.id.input_position);
        mInputEmailUser = (EditText) view.findViewById(R.id.input_email_rep);
        mInputPhoneUser = (EditText) view.findViewById(R.id.input_phone_rep);
        mInputCompany = (EditText) view.findViewById(R.id.input_company);

        mInputRegisteredCompany = (EditText) view.findViewById(R.id.input_registered_company);
        mInputAddress = (EditText) view.findViewById(R.id.input_address);
        mInputPostalCode = (EditText) view.findViewById(R.id.input_postal_code);
        mInputWebsite = (EditText) view.findViewById(R.id.input_website);
        mInputProductService = (EditText) view.findViewById(R.id.input_product_service);
        mInputYearEstablished = (EditText) view.findViewById(R.id.input_year_establised);
        mInputYearlyTurnOver = (EditText) view.findViewById(R.id.input_estimated_year_turnover);
        mInputTotalEmployee = (EditText) view.findViewById(R.id.input_estimated_total_employee);
        mInputCapabilities = (EditText) view.findViewById(R.id.input_capabilities);
        mInputHistory = (EditText) view.findViewById(R.id.input_history);
        mInputPhone = (EditText) view.findViewById(R.id.input_phone);
        mInputEmail = (EditText) view.findViewById(R.id.input_email);

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.setVisibility(View.VISIBLE);
                saveCompany();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbarScrollTrick(mHeaderView, mScrollView);
        mSalutations = new ArrayList<String>(3);
        mSalutations.add("Mr.");
        mSalutations.add("Mrs.");
        mSalutations.add("Ms.");
        mSelectSalutation.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mSalutations));

        mProvinces = new ArrayList<String>();
        mSelectProvince.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mProvinces));

        mCities = new ArrayList<String>();
        mSelectCity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCities));

        mIndustriesLvl1 = new ArrayList<String>();
        mSelectIndustryLvl1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mIndustriesLvl1));

        mIndustriesLvl2 = new ArrayList<String>();
        mSelectIndustryLvl2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mIndustriesLvl2));

        mIndustriesLvl3 = new ArrayList<String>();
        mSelectIndustryLvl3.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mIndustriesLvl3));

        mIndustriesLvl4 = new ArrayList<String>();
        mSelectIndustryLvl4.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mIndustriesLvl4));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapterGallery);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));

        getCompany();
    }

    @Override
    public void onDestroyView() {
        mHeaderView = mLoadingView = null;
        mScrollView = null;
        mSelectSalutation = mSelectProvince = mSelectCity =
                mSelectIndustryLvl1 = mSelectIndustryLvl2 = mSelectIndustryLvl3 = mSelectIndustryLvl4;
        mInputFirstname = mInputLastname = mInputPosition = mInputCompany = mInputEmailUser = mInputPhoneUser =
                mInputRegisteredCompany = mInputAddress = mInputPostalCode = mInputWebsite = mInputProductService =
                        mInputYearEstablished = mInputYearlyTurnOver = mInputTotalEmployee = mInputCapabilities = mInputHistory =
                                mInputPhone = mInputEmail;
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView image = (ImageView) mRecyclerView.getChildAt(mSelectedIndexGallery).findViewById(R.id.image);
            image.setImageBitmap(BitmapFactory.decodeStream(imageStream));
        }
    }

    private void getCompany() {
        SharedPreferences sp = getSharedPreferences();
        JacksonRequest<Company> request = JacksonRequest.<Company>method(Request.Method.GET)
                .url(App.BASE_URL + CompanyFragment.URL_COMPANY)
                .listener(new Response.Listener<Company>() {
                    @Override
                    public void onResponse(Company company) {
                        if (mScrollView == null) return;

                        setDataCompany(company);
                        mLoadingView.setVisibility(View.GONE);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                    }
                })
                .clazz(Company.class)
                .username(sp.getString(ProfileFragment.PREF_EMAIL, null))
                .password(sp.getString(ProfileFragment.PREF_PASSWORD, null))
                .build();
        request.setTag(getTag());
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void saveCompany() {
        String salutation = mSelectSalutation.getSelectedItem().toString();
        String firstname = mInputFirstname.getText().toString();
        String lastname = mInputLastname.getText().toString();
        String company = mInputCompany.getText().toString();
        String position = mInputPosition.getText().toString();
        String userPhone = mInputPhoneUser.getText().toString();
        String userEmail = mInputEmailUser.getText().toString();

        String registeredCompany = mInputRegisteredCompany.getText().toString();
        String address = mInputAddress.getText().toString();
        String postalCode = mInputPostalCode.getText().toString();
        String website = mInputWebsite.getText().toString();
        String productService = mInputProductService.getText().toString();
        int yearEstablished = Integer.parseInt(mInputYearEstablished.getText().toString());
        String yearlyTurnOver = mInputYearlyTurnOver.getText().toString();
        String totalEmployee = mInputTotalEmployee.getText().toString();
        String capabilities = mInputCapabilities.getText().toString();
        String history = mInputHistory.getText().toString();
        String phone = mInputPhone.getText().toString();
        String email = mInputEmail.getText().toString();

        Company c = new Company();
        c.setSalutation(salutation);
        c.setFirstname(firstname);
        c.setLastname(lastname);
        c.setCompany(company);
        c.setPosition(position);
        c.setUserPhone(userPhone);
        c.setUserEmail(userEmail);
        c.setRegisteredCompany(registeredCompany);
        c.setAddress(address);
        c.setPostalCode(postalCode);
        c.setWebsite(website);
        c.setProductService(productService);
        c.setYearEstablished(yearEstablished);
        c.setEstimatedYearlyTurnover(yearlyTurnOver);
        c.setEstimatedTotalEmployee(totalEmployee);
        c.setCapabilities(capabilities);
        c.setHistory(history);
        c.setPhone(phone);
        c.setEmail(email);

        SharedPreferences sp = getSharedPreferences();
        JacksonRequest<Company> request = JacksonRequest.<Company>method(Request.Method.POST)
                .url(App.BASE_URL + URL_SAVE_PROFILE)
                .listener(new Response.Listener<Company>() {
                    @Override
                    public void onResponse(Company company) {
                        if (mScrollView == null) return;

                        setDataCompany(company);
                        mLoadingView.setVisibility(View.GONE);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                    }
                })
                .clazz(Company.class)
                .username(sp.getString(ProfileFragment.PREF_EMAIL, null))
                .password(sp.getString(ProfileFragment.PREF_PASSWORD, null))
                .body(c)
                .build();
        request.setTag(getTag());
        Connection.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void setDataCompany(Company company) {
        mCompany = company;

        mSelectSalutation.setSelection(mSalutations.indexOf(company.getSalutation()));
        mInputFirstname.setText(company.getFirstname());
        mInputLastname.setText(company.getLastname());
        mInputCompany.setText(company.getCompany());
        mInputPosition.setText(company.getPosition());
        mInputEmailUser.setText(company.getUserEmail());
        mInputPhoneUser.setText(company.getUserPhone());

        mInputRegisteredCompany.setText(company.getRegisteredCompany());
        mInputAddress.setText(company.getAddress());
        mSelectProvince.setSelection(mSalutations.indexOf(company.getProvince()));
        mSelectCity.setSelection(mSalutations.indexOf(company.getCity()));
        mInputPostalCode.setText(company.getPostalCode()+"");
        mInputWebsite.setText(company.getWebsite());
        mSelectProvince.setSelection(mSalutations.indexOf(company.getIndustryLvl1()));
        mSelectProvince.setSelection(mSalutations.indexOf(company.getIndustryLvl2()));
        mSelectProvince.setSelection(mSalutations.indexOf(company.getIndustryLvl3()));
        mSelectProvince.setSelection(mSalutations.indexOf(company.getIndustryLvl4()));
        mInputProductService.setText(company.getProductService());
        mInputYearEstablished.setText(company.getYearEstablished()+"");
        mInputYearlyTurnOver.setText(company.getEstimatedYearlyTurnover());
        mInputTotalEmployee.setText(company.getEstimatedTotalEmployee());
        mInputCapabilities.setText(company.getCapabilities());
        mInputHistory.setText(company.getHistory());
        mInputEmail.setText(company.getEmail());
        mInputPhone.setText(company.getPhone());
        List<Gallery> gallery = new ArrayList<Gallery>(2 + company.getProductPhotos().size());
        gallery.add(new Gallery().setImage(company.getProfilePicture()).setTitle("Profile Picture"));
        gallery.add(new Gallery().setImage(company.getOfficePhoto()).setTitle("User Photo"));
        for (int i = 0; i < company.getProductPhotos().size(); i++) {
            Gallery g = company.getProductPhotos().get(i);
            g.setTitle("Product " + (i+1));
            gallery.add(g);
        }
        mAdapterGallery.setList(gallery);
    }

    private View.OnClickListener mBrowseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectedIndexGallery = (int) v.getTag();
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_IMAGE);
        }
    };

    private View.OnClickListener mUploadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
