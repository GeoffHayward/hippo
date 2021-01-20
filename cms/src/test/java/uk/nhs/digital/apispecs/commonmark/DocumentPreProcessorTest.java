package uk.nhs.digital.apispecs.commonmark;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.commonmark.node.Document;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class DocumentPreProcessorTest {

    @UseDataProvider("headingsLevels")
    @Test
    public void findsTopHeadingLevel(final int expectedTopHeadingLevel) {
        fail("Test not implemented, yet");

        // given
        final Document document = new Document();
        document.appendChild();


        final DocumentPreProcessor within = DocumentPreProcessor.within(document);

        // when
        final int actualTopHeadingLevel = within.findTopHeadingLevel();

        // then
        assertThat("Top heading level has been identified.",
            actualTopHeadingLevel,
            is(expectedTopHeadingLevel)
        );

    }

    @DataProvider
    public Object[][] headingsLevels() {

        return new Object[][]{
            // testCase                                 expectedTopHeadingLevel     headingsLevels
            {"top level heading first",                 2,                          asList(2, 3)},
            {"top level heading not first",             2,                          asList(3, 2, 4)},
            {"top level heading last",                  2,                          asList(3, 2)},
            {"multiple instances of the top heading"},
            {"level 1 top heading"},
            {"level 2 top heading"},
            {"level 3 top heading"},
            {"level 4 top heading"},
            {"level 5 top heading"},
            {"level 6 top heading"}
        };
    }
}