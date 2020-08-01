package org.fastrackit.onlineshop4shoes.transfer.review;

public class GetReviewsRequest {

    private String partialContent;
    private String author;


    public String getPartialContent() {
        return partialContent;
    }

    public void setPartialContent(String partialContent) {
        this.partialContent = partialContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "GetReviewsRequest{" +
                "partialContent='" + partialContent + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
