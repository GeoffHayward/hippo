package uk.nhs.digital.ps.test.acceptance.models;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import uk.nhs.digital.ps.test.acceptance.models.section.BodySection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess") // builder's methods are intentionally public
public class PublicationBuilder {

    private String name;
    private String title;
    private String summary;
    private GeographicCoverage geographicCoverage;
    private InformationType informationType;
    private Granularity granularity;
    private Instant nominalDate;
    private boolean publiclyAccessible;
    private List<AttachmentBuilder> attachmentBuilders = new ArrayList<>();
    private Taxonomy taxonomy;
    private List<BodySection> bodySections;

    private PublicationState state;

    public static PublicationBuilder newPublication() {
        return new PublicationBuilder();
    }

    //<editor-fold desc="BUILDER METHODS">
    public PublicationBuilder withName(final String name) {
        return cloneAndAmend(builder -> builder.name = name);
    }

    public PublicationBuilder withTitle(final String title) {
        return cloneAndAmend(builder -> builder.title = title);
    }

    public PublicationBuilder withSummary(final String summary) {
        return cloneAndAmend(builder -> builder.summary = summary);
    }

    public PublicationBuilder withGeographicCoverage(final GeographicCoverage geographicCoverage) {
        return cloneAndAmend(builder -> builder.geographicCoverage = geographicCoverage);
    }

    public PublicationBuilder withInformationType(final InformationType informationType) {
        return cloneAndAmend(builder -> builder.informationType = informationType);
    }

    public PublicationBuilder withGranularity(final Granularity granularity) {
        return cloneAndAmend(builder -> builder.granularity = granularity);
    }

    public PublicationBuilder withNominalDate(final Instant nominalDate) {
        return cloneAndAmend(builder -> builder.nominalDate = nominalDate);
    }

    public PublicationBuilder withAttachments(final List<AttachmentBuilder> attachmentBuilders) {
        return cloneAndAmend(builder -> builder.attachmentBuilders = attachmentBuilders);
    }
    public PublicationBuilder withAttachments(final AttachmentBuilder... attachmentBuilders) {
        return cloneAndAmend(builder -> builder.attachmentBuilders = asList(attachmentBuilders));
    }

    public PublicationBuilder inState(final PublicationState state) {
        return cloneAndAmend(builder -> builder.state = state);
    }

    public PublicationBuilder withTaxonomy(final Taxonomy taxonomy) {
        return cloneAndAmend(builder -> builder.taxonomy = taxonomy);
    }

    public PublicationBuilder withBodySections(final List<BodySection> bodySections) {
        return cloneAndAmend(builder -> builder.bodySections = bodySections);
    }

    public PublicationBuilder withPubliclyAccessible(final boolean publiclyAccessible) {
        return cloneAndAmend(builder -> builder.publiclyAccessible = publiclyAccessible);
    }
    //</editor-fold>

    public Publication build() {
        return new Publication(this);
    }

    //<editor-fold desc="GETTERS" defaultstate="collapsed">
    String getName() {
        return name;
    }

    String getTitle() {
        return title;
    }

    String getSummary() {
        return summary;
    }

    GeographicCoverage getGeographicCoverage() {
        return geographicCoverage;
    }

    InformationType getInformationType() {
        return informationType;
    }

    Granularity getGranularity() {
        return granularity;
    }

    Taxonomy getTaxonomy() {
        return taxonomy;
    }

    Instant getNominalDate() {
        return nominalDate;
    }

    List<Attachment> getAttachments() {
        return getAttachmentBuilders().stream().map(AttachmentBuilder::build).collect(toList());
    }

    List<AttachmentBuilder> getAttachmentBuilders() {
        return new ArrayList<>(attachmentBuilders);
    }

    PublicationState getState() {
        return state;
    }

    public List<BodySection> getBodySections() {
        return bodySections;
    }

    public boolean isPubliclyAccessible() {
        return publiclyAccessible;
    }
    //</editor-fold>

    private PublicationBuilder(final PublicationBuilder original) {
        name = original.getName();
        title = original.getTitle();
        summary = original.getSummary();
        geographicCoverage = original.getGeographicCoverage();
        informationType = original.getInformationType();
        granularity = original.getGranularity();
        nominalDate = original.getNominalDate();
        publiclyAccessible = original.isPubliclyAccessible();
        taxonomy = original.getTaxonomy();
        attachmentBuilders = original.getAttachmentBuilders();
        bodySections = original.getBodySections();

        state = original.getState();
    }

    private PublicationBuilder() {
        // no-op; made private to promote the use of factory methods
    }

    private PublicationBuilder cloneAndAmend(final PropertySetter propertySetter) {
        final PublicationBuilder clone = new PublicationBuilder(this);
        propertySetter.setProperties(clone);
        return clone;
    }

    @FunctionalInterface
    interface PropertySetter {
        void setProperties(PublicationBuilder builder);
    }

    public static Collection collectionOf(final PublicationBuilder... publicationBuilders) {
        return new Collection(publicationBuilders);
    }

    public static class Collection {

        private final List<PublicationBuilder> publicationBuilders;

        Collection(final PublicationBuilder... publicationBuilders) {
            this.publicationBuilders = asList(publicationBuilders);
        }

        public List<Publication> build() {
            return publicationBuilders.stream().map(PublicationBuilder::build).collect(toList());
        }
    }
}
