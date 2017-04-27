/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by renjaya on 5/11/15.
 */
public class Company implements Parcelable {

    private long id;
    private String salutation;
    private String firstname;
    private String lastname;
    private String position;
    private String userPhone;
    private String userEmail;
    private String company;
    private String registeredCompany;
    private String address;
    private int provinceCode;
    private String province;
    private int cityCode;
    private String city;
    private String postalCode;
    private String productService;
    private int yearEstablished;
    private String estimatedYearlyTurnover;
    private String estimatedTotalEmployee;
    private String capabilities;
    private String history;
    private String phone;
    private String email;
    private String website;
    private String profilePicture;
    private String officePhoto;
    private String business_license;
    private String company_register;
    private String tax_number;
    private String industryLvl1;
    private String industryLvl2;
    private String industryLvl3;
    private String industryLvl4;
    private int is_supplier;
    private int is_investor;
    private int is_premium;
    private int loved;
    private List<Gallery> productPhotos;
    private List<Opportunity> opportunities;
    private List<Company> connections;


    public Company() {
    }

    public Company(Parcel source) {
        this.id = source.readLong();
        this.salutation = source.readString();
        this.firstname = source.readString();
        this.lastname = source.readString();
        this.position = source.readString();
        this.userEmail = source.readString();
        this.userPhone = source.readString();
        this.company = source.readString();
        this.registeredCompany = source.readString();
        this.address = source.readString();
        this.provinceCode = source.readInt();
        this.province = source.readString();
        this.cityCode = source.readInt();
        this.city = source.readString();
        this.postalCode = source.readString();
        this.productService = source.readString();
        this.yearEstablished = source.readInt();
        this.estimatedYearlyTurnover = source.readString();
        this.estimatedTotalEmployee = source.readString();
        this.capabilities = source.readString();
        this.history = source.readString();
        this.phone = source.readString();
        this.email = source.readString();
        this.website = source.readString();
        this.profilePicture = source.readString();
        this.officePhoto = source.readString();
        this.business_license = source.readString();
        this.company_register = source.readString();
        this.tax_number = source.readString();
        this.industryLvl1 = source.readString();
        this.industryLvl2 = source.readString();
        this.industryLvl3 = source.readString();
        this.industryLvl4 = source.readString();
        this.is_supplier = source.readInt();
        this.is_investor = source.readInt();
        this.is_premium = source.readInt();
        this.loved = source.readInt();
        this.productPhotos = source.readArrayList(Gallery.class.getClassLoader());
        this.connections = source.readArrayList(Company.class.getClassLoader());
        this.opportunities = source.readArrayList(Opportunity.class.getClassLoader());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegisteredCompany() {
        return registeredCompany;
    }

    public void setRegisteredCompany(String registeredCompany) {
        this.registeredCompany = registeredCompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public int getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(int yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public String getEstimatedYearlyTurnover() {
        return estimatedYearlyTurnover;
    }

    public void setEstimatedYearlyTurnover(String estimatedYearlyTurnover) {
        this.estimatedYearlyTurnover = estimatedYearlyTurnover;
    }

    public String getEstimatedTotalEmployee() {
        return estimatedTotalEmployee;
    }

    public void setEstimatedTotalEmployee(String estimatedTotalEmployee) {
        this.estimatedTotalEmployee = estimatedTotalEmployee;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getOfficePhoto() {
        return officePhoto;
    }

    public void setOfficePhoto(String officePhoto) {
        this.officePhoto = officePhoto;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getCompany_register() {
        return company_register;
    }

    public void setCompany_register(String company_register) {
        this.company_register = company_register;
    }

    public String getTax_number() {
        return tax_number;
    }

    public void setTax_number(String tax_number) {
        this.tax_number = tax_number;
    }

    public String getIndustryLvl1() {
        return industryLvl1;
    }

    public void setIndustryLvl1(String industryLvl1) {
        this.industryLvl1 = industryLvl1;
    }

    public String getIndustryLvl2() {
        return industryLvl2;
    }

    public void setIndustryLvl2(String industryLvl2) {
        this.industryLvl2 = industryLvl2;
    }

    public String getIndustryLvl3() {
        return industryLvl3;
    }

    public void setIndustryLvl3(String industryLvl3) {
        this.industryLvl3 = industryLvl3;
    }

    public String getIndustryLvl4() {
        return industryLvl4;
    }

    public void setIndustryLvl4(String industryLvl4) {
        this.industryLvl4 = industryLvl4;
    }

    public int getIs_supplier() {
        return is_supplier;
    }

    public void setIs_supplier(int is_supplier) {
        this.is_supplier = is_supplier;
    }

    public int getIs_investor() {
        return is_investor;
    }

    public void setIs_investor(int is_investor) {
        this.is_investor = is_investor;
    }

    public int getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(int is_premium) {
        this.is_premium = is_premium;
    }

    public int getLoved() {
        return loved;
    }

    public void setLoved(int loved) {
        this.loved = loved;
    }

    public List<Gallery> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(List<Gallery> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public List<Company> getConnections() {
        return connections;
    }

    public void setConnections(List<Company> connections) {
        this.connections = connections;
    }

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(company);
        dest.writeString(registeredCompany);
        dest.writeString(address);
        dest.writeInt(provinceCode);
        dest.writeString(province);
        dest.writeInt(cityCode);
        dest.writeString(city);
        dest.writeString(postalCode);
        dest.writeString(productService);
        dest.writeInt(yearEstablished);
        dest.writeString(estimatedYearlyTurnover);
        dest.writeString(estimatedTotalEmployee);
        dest.writeString(capabilities);
        dest.writeString(history);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(website);
        dest.writeString(profilePicture);
        dest.writeString(officePhoto);
        dest.writeString(business_license);
        dest.writeString(company_register);
        dest.writeString(tax_number);
        dest.writeString(industryLvl1);
        dest.writeString(industryLvl2);
        dest.writeString(industryLvl3);
        dest.writeString(industryLvl4);
        dest.writeInt(is_supplier);
        dest.writeInt(is_investor);
        dest.writeInt(is_premium);
        dest.writeTypedList(productPhotos);
        dest.writeTypedList(connections);
        dest.writeTypedList(opportunities);
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel source) {
            return new Company(source);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}
