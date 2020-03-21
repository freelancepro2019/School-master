package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AccountBalanceDataModel {

    @SerializedName("name")
    private String mName;
    @SerializedName("Net_due")
    private int mNetDue;
    @SerializedName("Net_Payout")
    private int mNetPayout;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("previous_balance")
    private int mPreviousBalance;
    @SerializedName("Residual")
    private int mResidual;
    @SerializedName("rowLavel")
    private String mRowLavel;
    @SerializedName("Total_benefits")
    private int mTotalBenefits;
    @SerializedName("Total_deductions")
    private int mTotalDeductions;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNetDue() {
        return mNetDue;
    }

    public void setNetDue(int netDue) {
        mNetDue = netDue;
    }

    public int getNetPayout() {
        return mNetPayout;
    }

    public void setNetPayout(int netPayout) {
        mNetPayout = netPayout;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getPreviousBalance() {
        return mPreviousBalance;
    }

    public void setPreviousBalance(int previousBalance) {
        mPreviousBalance = previousBalance;
    }

    public int getResidual() {
        return mResidual;
    }

    public void setResidual(int residual) {
        mResidual = residual;
    }

    public String getRowLavel() {
        return mRowLavel;
    }

    public void setRowLavel(String rowLavel) {
        mRowLavel = rowLavel;
    }

    public int getTotalBenefits() {
        return mTotalBenefits;
    }

    public void setTotalBenefits(int totalBenefits) {
        mTotalBenefits = totalBenefits;
    }

    public int getTotalDeductions() {
        return mTotalDeductions;
    }

    public void setTotalDeductions(int totalDeductions) {
        mTotalDeductions = totalDeductions;
    }

}