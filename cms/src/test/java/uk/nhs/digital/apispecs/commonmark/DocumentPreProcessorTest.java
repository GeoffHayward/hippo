package uk.nhs.digital.apispecs.commonmark;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Document;
import org.commonmark.node.Heading;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
public class DocumentPreProcessorTest {

    @UseDataProvider("headingsLevels")
    @Test
    public void findTopHeadingLevel_returnsSmallestValueOfLevelsFoundAmongAllHeadings(
        final String testCase,
        final int expectedTopHeadingLevel,
        final List<Integer> headingsLevels
    ) {
        // given
        final Document documentWithHeadings = documentWithHeadingsWith(headingsLevels);

        final DocumentPreProcessor preProcessor = DocumentPreProcessor.within(documentWithHeadings);

        // when
        final int actualTopHeadingLevel = preProcessor.findTopHeadingLevel();

        // then
        assertThat("For a document with " + testCase + ", the smallest of all heading levels should be returned.",
            actualTopHeadingLevel,
            is(expectedTopHeadingLevel)
        );
    }

    @DataProvider
    public static Object[][] headingsLevels() {

        // @formatter:off
        return new Object[][]{
            // testCase                                    expectedTopHeadingLevel     headingsLevels
            {"top level heading being the first one",      2,                          asList(2, 3)},
            {"top level heading being not the first one",  2,                          asList(3, 2, 4)},
            {"top level heading being on the last one",    2,                          asList(3, 2)},
            {"multiple instances of the top heading",      2,                          asList(3, 2, 4, 2, 2, 5)},
            {"h1 as the top heading in the hierarchy",     1,                          asList(1, 2)},
            {"h2 as the top heading in the hierarchy",     2,                          asList(2, 3)},
            {"h3 as the top heading in the hierarchy",     3,                          asList(3, 4)},
            {"h4 as the top heading in the hierarchy",     4,                          asList(4, 5)},
            {"h5 as the top heading in the hierarchy",     5,                          asList(5, 6)},
            {"h6 as the top heading in the hierarchy",     6,                          asList(6, 7)}
        };
        // @formatter:on
    }

    @Test
    public void findTopHeadingLevel_returnsZeroWhenDocumentContainsNoHeadings() {

        // given
        final Document documentWithNoHeadings = new Document();

        final DocumentPreProcessor preProcessor = DocumentPreProcessor.within(documentWithNoHeadings);

        // when
        final int actualTopHeadingLevel = preProcessor.findTopHeadingLevel();

        // then
        assertThat("Zero is returned when no headings are found in the document.",
            actualTopHeadingLevel,
            is(0)
        );
    }

    @UseDataProvider("offsets")
    @Test
    public void shiftHeadingsLevelsBy_addsGivenOffsetToLevelsOfAllHeadingsInDocument(
        final String testCase,
        final int offset,
        final List<Integer> initialHeadingsLevels,
        final List<Integer> expectedHeadingsLevels
    ) {
        // given
        final Document documentWithHeadings = documentWithHeadingsWith(initialHeadingsLevels);

        final DocumentPreProcessor preProcessor = DocumentPreProcessor.within(documentWithHeadings);

        // when
        preProcessor.shiftHeadingsLevelsBy(offset);

        // then
        final List<Integer> actualHeadingsLevels = headingsLevelsFrom(documentWithHeadings);

        assertThat("For a document with " + testCase + ", the smallest of all heading levels should be returned.",
            actualHeadingsLevels,
            is(expectedHeadingsLevels)
        );
    }

    private List<Integer> headingsLevelsFrom(final Document documentWithHeadings) {

        final List<Heading> headings = new ArrayList<>();

        documentWithHeadings.accept(new AbstractVisitor() {
            @Override public void visit(final Heading heading) {
                headings.add(heading);
                visitChildren(heading);
            }
        });

        return headings.stream().map(Heading::getLevel).collect(toList());
    }

    @DataProvider
    public static Object[][] offsets() {

        // @formatter:off
        return new Object[][]{
            // Note values which push level values beyond h1-h6 range. These are considered so unlikely that we
            // don't defend against them. It's up to the caller to supply 'reasonable' values; moreover, 'illegal'
            // headers such as <h7> or <h-2> don't break rendering and can be easily styled with CSS if needs be.

            // testCase         offset initialHeadingsLevels                      expectedHeadingsLevels
            {"no offset",        0,    asList(1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1),   asList( 1, 2, 3, 4, 5, 6, 5, 4, 3, 2,  1)},
            {"positive offset",  2,    asList(1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1),   asList( 3, 4, 5, 6, 7, 8, 7, 6, 5, 4,  3)},
            {"negative offset", -2,    asList(1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1),   asList(-1, 0, 1, 2, 3, 4, 3, 2, 1, 0, -1)},
        };
        // @formatter:on
    }

    private Document documentWithHeadingsWith(final List<Integer> headingsLevels) {
        final Document document = new Document();
        headingsLevels.forEach(headingLevel -> {
            final Heading heading = new Heading();
            heading.setLevel(headingLevel);
            document.appendChild(heading);
        });
        return document;
    }
}