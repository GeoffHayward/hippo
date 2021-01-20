package uk.nhs.digital.apispecs.commonmark;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;

import java.util.concurrent.atomic.AtomicInteger;

public class DocumentPreProcessor {

    private final Node document;

    private DocumentPreProcessor(final Node document) {
        this.document = document;
    }

    public static DocumentPreProcessor within(final Node document) {
        return new DocumentPreProcessor(document);
    }

    public int findTopHeadingLevel() {

        final AtomicInteger topHeadingLevel = new AtomicInteger();

        final AbstractVisitor topHeadingLevelFinder = new AbstractVisitor() {

            @Override public void visit(final Heading heading) {

                if (topHeadingLevel.get() == 0 || heading.getLevel() < topHeadingLevel.get()) {
                    topHeadingLevel.set(heading.getLevel());
                }

                visitChildren(heading);
            }
        };

        document.accept(topHeadingLevelFinder);

        return topHeadingLevel.get();
    }

    public void shiftHeadingsLevelsBy(final int headingsLevelsOffset) {

        final AbstractVisitor headingsLevelsShifter = new AbstractVisitor() {

            @Override public void visit(final Heading heading) {

                heading.setLevel(heading.getLevel() + headingsLevelsOffset);

                visitChildren(heading);
            }
        };

        document.accept(headingsLevelsShifter);
    }
}
