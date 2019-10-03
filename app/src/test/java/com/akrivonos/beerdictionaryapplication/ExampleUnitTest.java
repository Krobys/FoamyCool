package com.akrivonos.beerdictionaryapplication;

import com.akrivonos.beerdictionaryapplication.utils.UrlUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {

    @Test
    public void fixUrlTool_isCorrect() {
        String expectedUrl = "https://brewerydb-images.s3.amazonaws.com/beer/Fhw2NF/upload_4QscTY-medium.png";
        String sourceUrl = "https:\\/\\/brewerydb-images.s3.amazonaws.com\\/beer\\/Fhw2NF\\/upload_4QscTY-medium.png";
        assertEquals(expectedUrl, UrlUtils.makeUrlIconFix(sourceUrl));
    }

    @Test
    public void fixUrlTool_isCorrectWhenNull() {
        String expectedUrl = "";
        String sourceUrl = null;
        assertEquals(expectedUrl, UrlUtils.makeUrlIconFix(sourceUrl));
    }

    @Test
    public void fixUrlTool_isCorrectWhenEmpty() {
        String expectedUrl = "";
        String sourceUrl = "";
        assertEquals(expectedUrl, UrlUtils.makeUrlIconFix(sourceUrl));
    }


}