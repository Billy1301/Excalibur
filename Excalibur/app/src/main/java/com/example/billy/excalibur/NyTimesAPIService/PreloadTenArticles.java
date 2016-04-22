//package com.example.billy.excalibur.NyTimesAPIService;
//
//import android.util.Log;
//
//import com.example.billy.excalibur.MainActivity;
//
///**
// * Created by petermartinez on 4/18/16.
// */
//public class PreloadTenArticles {
//
//    public static void preloadArticles() {
//        if (MainActivity.articleLists.size() == 0) {
//            for (int i = 0; i < 9; i++) {
//                MainActivity.articleLists.add(getPreloadArticle(i));
//                //Log.i("Preload", "loaded number " + i);
//            }
//        }
//    }
//
//
//    private static NewsWireObjects getPreloadArticle(int i){
//        NewsWireObjects article;
//        switch (i) {
//            case 0:
//                article = new NewsWireObjects(
//                        "Business",
//                        "2016 Pulitzer Prize Winners",
//                        "http://www.nytimes.com/interactive/2016/04/19/business/media/pulitzer-prize-winners-complete-list.html",
//                        "https://static01.nyt.com/images/2016/04/18/blogs/19pulitzers1alt/18-lens-refugees-slide-6010-thumbStandard.jpg",
//                        "The Pulitzers are in their centennial year, and the winners announced on Monday reflected in part the changes sweeping the media landscape. Here is the full list.",
//                );
//                return article;
//            case 1:
//                article = new NewsWireObjects(
//                        "Health",
//                        "AIDS Treatment in Haiti Promising for Developing Nations",
//                        "http://www.nytimes.com/2016/04/19/health/aids-treatment-in-haiti-promising-for-developing-nations.html",
//                        "http://graphics8.nytimes.com/packages/flash/Lens/2010/11/20101118-KDS-Showcase-Andre/20101118-KDS-Showcase-Andre-embed-350px.jpg",
//                        "A program providing free medicines has produced a survival rate comparable to that of gay men in the United States.",
//                );
//                return article;
//            case 2:
//                article = new NewsWireObjects(
//                        "U.S.",
//                        "Old Houses From Canada Become New Homes in Washington State",
//                        "http://www.nytimes.com/2016/04/19/us/old-houses-from-canada-become-new-homes-in-washington-state.html",
//                        "https://static01.nyt.com/images/2016/04/17/us/00bargehouse-web02/00bargehouse-web02-thumbStandard.jpg",
//                        "Houses from British Columbia have been taken by barge and restored in the San Juan Islands to help give struggling families an affordable way to stay."
//                );
//                return article;
//            case 3:
//                article = new NewsWireObjects(
//                        "Arts",
//                        "Atop the Met, a Haunting House",
//                        "http://www.nytimes.com/2016/04/19/arts/design/a-ghostly-house-appears-in-the-mets-roof-garden.html",
//                        "https://static01.nyt.com/images/2016/04/19/arts/19METROOFJP-SUB/19METROOFJP-SUB-thumbStandard-v3.jpg",
//                        "Cornelia Parker’s installation in the Metropolitan Museum of Art roof garden evokes the spooky mansion in “Psycho,” as well as an earlier America."
//                );
//                return article;
//            case 4:
//                article = new NewsWireObjects(
//                        "Sports",
//                        "With a Discreet Motor, Doping the Bike Instead of the Cyclist",
//                        "https://static01.nyt.com/images/2016/04/19/sports/19MOTORDOPINGweb1/19MOTORDOPINGweb1-thumbStandard.jpg",
//                        "http://www.nytimes.com/2016/04/19/sports/cycling/with-a-discreet-motor-doping-the-bike-instead-of-the-cyclist.html",
//                        "A report by a French television network on Sunday suggested that motordoping has arrived at the highest levels of the sport."
//                );
//                return article;
//            case 5:
//                article = new NewsWireObjects(
//                        "Science",
//                        "Do Honeybees Feel? Scientists Are Entertaining the Idea",
//                        "http://www.nytimes.com/2016/04/19/science/honeybees-insects-consciousness-brains.html",
//                        "https://static01.nyt.com/images/2016/04/19/science/19HONEYBEE/19HONEYBEE-thumbStandard.jpg",
//                        "An Australian scientist and a philosopher propose that the structure of insect brains suggests they have the capacity for basic awareness."
//                );
//                return article;
//            case 6:
//                article = new NewsWireObjects(
//                        "Health",
//                        "In IVF, a Move Toward Using More ‘Mosaic’ Embryos",
//                        "http://www.nytimes.com/2016/04/19/health/ivf-in-vitro-fertilization-pregnancy-abnormal-embryos-mosaic.html",
//                        "https://static01.nyt.com/images/2016/04/19/science/19EMBRYOS/19EMBRYOS-thumbStandard.jpg",
//                        "New technology more accurately reveals mosaic embryos, those with normal and abnormal cells. That raises issues of whether they should be implanted."
//                );
//                return article;
//            case 7:
//                article = new NewsWireObjects(
//                        "Business",
//                        "Ad Agencies Need Young Talent. Cue the Bean-Bag Chairs.",
//                        "http://www.nytimes.com/2016/04/19/business/media/ad-agencies-need-young-talent-cue-the-bean-bag-chairs.html",
//                        "https://static01.nyt.com/images/2016/04/19/business/19YOUNG1/19YOUNG1-thumbStandard.jpg",
//                        "To enhance their appeal, agencies are trying to make themselves look less like Madison Avenue and more like the start-ups and tech companies."
//                );
//                return article;
//            case 8:
//                article = new NewsWireObjects(
//                        "Arts",
//                        "You Don’t Like the Girls in ‘Girls’? That’s Its Genius.",
//                        "http://www.nytimes.com/2016/04/19/arts/television/you-dont-like-the-girls-in-girls-but-thats-its-genius.html",
//                        "https://static01.nyt.com/images/2016/04/19/arts/19GIRLSFINALEJP/19GIRLSFINALEJP-thumbStandard.jpg",
//                        "Through 52 episodes, the show has never stopped looking for the grander, harsher psychological story. And, it never stopped looking for tough laughs."
//
//                );
//                return article;
//            default:
//                article = new NewsWireObjects(
//                        "Business",
//                        "2016 Pulitzer Prize Winners",
//                        "http://www.nytimes.com/interactive/2016/04/19/business/media/pulitzer-prize-winners-complete-list.html",
//                        "https://static01.nyt.com/images/2016/04/18/blogs/19pulitzers1alt/18-lens-refugees-slide-6010-thumbStandard.jpg",
//                        "The Pulitzers are in their centennial year, and the winners announced on Monday reflected in part the changes sweeping the media landscape. Here is the full list."
//                );
//                return article;
//        }
//    }
//}
