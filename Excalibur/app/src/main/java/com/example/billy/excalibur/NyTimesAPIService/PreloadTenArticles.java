package com.example.billy.excalibur.NyTimesAPIService;

/**
 * Created by petermartinez on 4/18/16.
 */
public class PreloadTenArticles {

    public static NewsWireObjects getPreloadArticle(int i){
        NewsWireObjects article;
        switch (i) {
            case 0:
                article = new NewsWireObjects(
                        "Business",
                        "2016 Pulitzer Prize Winners",
                        "http://www.nytimes.com/interactive/2016/04/19/business/media/pulitzer-prize-winners-complete-list.html",
                        "https://static01.nyt.com/images/2016/04/18/blogs/19pulitzers1alt/18-lens-refugees-slide-6010-thumbStandard.jpg",
                        "The Pulitzers are in their centennial year, and the winners announced on Monday reflected in part the changes sweeping the media landscape. Here is the full list."
                );
                return article;

            default:
                article = new NewsWireObjects(
                        "Business",
                        "2016 Pulitzer Prize Winners",
                        "http://www.nytimes.com/interactive/2016/04/19/business/media/pulitzer-prize-winners-complete-list.html",
                        "https://static01.nyt.com/images/2016/04/18/blogs/19pulitzers1alt/18-lens-refugees-slide-6010-thumbStandard.jpg",
                        "The Pulitzers are in their centennial year, and the winners announced on Monday reflected in part the changes sweeping the media landscape. Here is the full list."
                );
                return article;
        }
    }
}
