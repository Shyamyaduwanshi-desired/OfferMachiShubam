package com.desired.offermachi.retalier.model;

public class FAQ {
    private String id;
    private String post_title;
    private String post_body;


    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public void setPost_body(String post_body) {
        this.post_body = post_body;
    }

    public String getPost_body() {
        return post_body;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FAQ(String id, String post_title, String post_body) {
        this.id = id;
        this.post_title = post_title;
        this.post_body = post_body;

    }


}

