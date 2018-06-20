package appdeveloper.klop.klop.Model;

import java.io.Serializable;

/**
 * Created by CMDDJ on 12/4/2017.
 */

public class UserBody implements Serializable {
    private int intId_user;
    private String strFullname;
    private String strPassword;
    private String strPhone;
    private String strEmail;
    private String strStatus;
    private int intId_role;
    private String strGender;
    private String strAvatar;
//    private String strAvatarName;
    private String strCreated;

    public int getIntId_user() {
        return intId_user;
    }

    public void setIntId_user(int intId_user) {
        this.intId_user = intId_user;
    }

    public String getStrFullname() {
        return strFullname;
    }

    public void setStrFullname(String strFullname) {
        this.strFullname = strFullname;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public int getIntId_role() {
        return intId_role;
    }

    public void setIntId_role(int intId_role) {
        this.intId_role = intId_role;
    }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public String getStrAvatar() {
        return strAvatar;
    }

    public void setStrAvatar(String strAvatar) {
        this.strAvatar = strAvatar;
    }

    public String getStrCreated() {
        return strCreated;
    }

    public void setStrCreated(String strCreated) {
        this.strCreated = strCreated;
    }
//
//    public String getStrAvatarName() {
//        return strAvatarName;
//    }
//
//    public void setStrAvatarName(String strAvatarName) {
//        this.strAvatarName = strAvatarName;
//    }
}
