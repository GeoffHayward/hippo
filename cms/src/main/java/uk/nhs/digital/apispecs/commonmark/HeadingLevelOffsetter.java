package uk.nhs.digital.apispecs.commonmark;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;

public class HeadingLevelOffsetter extends AbstractVisitor { // rktodo name

    private final Node document;
    private int headingsOffset;

    private HeadingLevelOffsetter(final Node document) {
        this.document = document;
    }

    public static HeadingLevelOffsetter within(final Node document) {
        return new HeadingLevelOffsetter(document);
    }

    public void shiftHeadingsByOffset(final int headingsOffset) {
        this.headingsOffset = headingsOffset;
        document.accept(this);
    }

    @Override public void visit(final Heading heading) {

        heading.setLevel(heading.getLevel() + headingsOffset);

        visitChildren(heading);
    }
}
