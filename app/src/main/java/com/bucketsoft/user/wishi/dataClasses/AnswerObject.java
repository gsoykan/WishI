package com.bucketsoft.user.wishi.dataClasses;

import java.io.Serializable;
import java.util.Date;

public class AnswerObject implements Serializable {

    private Date answerDate;
    private String answerBody;
    private  String answererUid;
    private String answereDisplayName;

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public String getAnswererUid() {
        return answererUid;
    }

    public void setAnswererUid(String answererUid) {
        this.answererUid = answererUid;
    }

    public String getAnswereDisplayName() {
        return answereDisplayName;
    }

    public void setAnswereDisplayName(String answereDisplayName) {
        this.answereDisplayName = answereDisplayName;
    }
}
