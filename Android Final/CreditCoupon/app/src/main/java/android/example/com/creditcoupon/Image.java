package android.example.com.creditcoupon;

public class Image {

    // Member variables representing the title and information about the sport.
    //若要加描述內容，得從這開始加 然後記得在string中加、adapter初始化.......
    private String title;
    private String content;
    private String uri;
    private String imageResource;

    public Image(String title, String content, String uri,String imageResource) { //  , String content, String uri,
        this.title = title;
        this.content = content;
        this.uri = uri;
        this.imageResource = imageResource;
    }

    String getTitle() {
        return title;
    }
    String getContent() {
        return content;
    }
    String getUri() {
        return uri;
    }
    String getImageResource() {
        return imageResource;
    }

}
