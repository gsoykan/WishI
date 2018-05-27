package com.bucketsoft.user.wishi.dataClasses;

public class WisherUser {

    private Integer age;
    private String bio;
    private String email;
    private Boolean infoCheck;
    private String photoURL;
    private String uid;
    private String displayName;

    public WisherUser() {
    }

    public WisherUser(String email, String uid) {
        this.email = email;
        this.uid = uid;

    }

    public Boolean getInfoCheck() {
        return infoCheck;
    }

    public void setInfoCheck(Boolean infoCheck) {
        this.infoCheck = infoCheck;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
