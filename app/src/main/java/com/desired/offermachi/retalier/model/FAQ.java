package com.desired.offermachi.retalier.model;

public class FAQ {
    private String question;
    private String ans;
    public FAQ(String question, String ans) {
        this.question = question;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public String getAns() {
        return ans;
    }
}
