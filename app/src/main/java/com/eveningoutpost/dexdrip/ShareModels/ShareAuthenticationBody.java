package com.eveningoutpost.dexdrip.ShareModels;

import com.google.gson.annotations.Expose;

/**
 * Created by stephenblack on 3/16/15.
 */
class ShareAuthenticationBody {
    @Expose
    private String password;

    @Expose
    private String applicationId;

    @Expose
    private String accountName;

    public ShareAuthenticationBody(String aPassword, String aAccountName) {
        this.password = aPassword;
        this.accountName = aAccountName;
        this.applicationId = "d89443d2-327c-4a6f-89e5-496bbb0317db";
    }
}
