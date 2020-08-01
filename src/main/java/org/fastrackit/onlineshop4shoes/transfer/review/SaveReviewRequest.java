package org.fastrackit.onlineshop4shoes.transfer.review;

import javax.validation.constraints.NotNull;

public class SaveReviewRequest {

    private long id;
    @NotNull
    private String content;
    private String author;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "SaveReviewRequest{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
