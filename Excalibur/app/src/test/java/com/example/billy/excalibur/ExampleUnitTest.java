package com.example.billy.excalibur;

import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

        @Test
    public void checkNewsWireObjects() {
        NewsWireObjects section = new NewsWireObjects("Section Name", "Headline Title", "www.http.com", "drawable.launcher", "article info", "dated: 12:25");

        String expected = "Section Name";
        String actual = section.getSection();

        assertEquals(expected, actual);
    }

    @Test
    public void checkNewsWireHeadline() {
        NewsWireObjects headline = new NewsWireObjects("Section Name", "Headline Title", "www.http.com", "drawable.launcher", "article info","dated: 12:25");

        String expected = "Headline Title";
        String actual = headline.getTitle();

        assertEquals(expected, actual);

    }

    @Test
    public void checkNewsWireURL() {
        NewsWireObjects url = new NewsWireObjects("Section Name", "Headline Title", "www.http.com", "drawable.launcher", "article info","dated: 12:25");

        String expected = "www.http.com";
        String actual = url.getUrl();

        assertEquals(expected, actual);

    }

    @Test
    public void checkNewsWireDrawable() {
        NewsWireObjects drawable = new NewsWireObjects("Section Name", "Headline Title", "www.http.com", "drawable.launcher", "article info","dated: 12:25");

        String expected = "drawable.launcher";
        String actual = drawable.getThumbnail_standard();

        assertEquals(expected, actual);

    }



}