package uk.nhs.digital.apispecs.commonmark;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;

public class TopHeadingLevelFinder extends AbstractVisitor {

    private final Node document;

    private int topHeadingLevel = 0;

    private TopHeadingLevelFinder(final Node document) {
        this.document = document;
    }

    public static TopHeadingLevelFinder within(final Node document) {
        return new TopHeadingLevelFinder(document);
    }

    @Override public void visit(final Heading heading) {

        if (topHeadingLevel == 0 || heading.getLevel() < topHeadingLevel) {
            topHeadingLevel = heading.getLevel();
        }

        visitChildren(heading);
    }

    public int findTopHeadingLevel() {

        document.accept(this);

        return topHeadingLevel;
    }
}
