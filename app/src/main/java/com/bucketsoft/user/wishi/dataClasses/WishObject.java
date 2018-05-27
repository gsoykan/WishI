package com.bucketsoft.user.wishi.dataClasses;

import android.widget.SimpleCursorTreeAdapter;

import java.util.ArrayList;
import java.util.Date;

public class WishObject {


    private  String wisherUid;
    private String wisherDisplayName;
    private  String question;
    private ArrayList<AnswerObject> answers = new ArrayList<>();
    private  String category;
    private Integer wisherAge;
    private Date wishDate;
    private String wishId;

    public WishObject() {
    }

    public WishObject(String wisherUid, String wisherDisplayName, String question, String category, Integer wisherAge, Date wishDate) {
        this.wisherUid = wisherUid;
        this.wisherDisplayName = wisherDisplayName;
        this.question = question;
        this.category = category;
        this.wisherAge = wisherAge;
        this.wishDate = wishDate;

    }


    public String getWishId() {
        return wishId;
    }

    public void setWishId(String wishId) {
        this.wishId = wishId;
    }

    public void addAnswer(AnswerObject answer){

        answers.add(answer);

    }


    public String getWisherUid() {
        return wisherUid;
    }

    public void setWisherUid(String wisherUid) {
        this.wisherUid = wisherUid;
    }

    public String getWisherDisplayName() {
        return wisherDisplayName;
    }

    public void setWisherDisplayName(String wisherDisplayName) {
        this.wisherDisplayName = wisherDisplayName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<AnswerObject> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerObject> answers) {
        this.answers = answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getWisherAge() {
        return wisherAge;
    }

    public void setWisherAge(Integer wisherAge) {
        this.wisherAge = wisherAge;
    }

    public Date getWishDate() {
        return wishDate;
    }

    public void setWishDate(Date wishDate) {
        this.wishDate = wishDate;
    }
}
