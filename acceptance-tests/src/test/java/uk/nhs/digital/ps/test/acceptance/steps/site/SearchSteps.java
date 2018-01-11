package uk.nhs.digital.ps.test.acceptance.steps.site;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.nhs.digital.ps.test.acceptance.data.TestDataRepo;
import uk.nhs.digital.ps.test.acceptance.pages.site.common.SearchPage;
import uk.nhs.digital.ps.test.acceptance.pages.widgets.SearchResultWidget;
import uk.nhs.digital.ps.test.acceptance.steps.AbstractSpringSteps;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;


public class SearchSteps extends AbstractSpringSteps {

    private final static Logger log = getLogger(SearchSteps.class);

    @Autowired
    private SearchPage searchPage;

    @Autowired
    private TestDataRepo testDataRepo;

    @When("^I search for \"([^\"]+)\"$")
    public void iSearchFor(String searchTerm) throws Throwable {
        searchPage.searchForTerm(searchTerm);
    }

    @When("^I search for the publication$")
    public void iSearchForThePublication() throws Throwable {
        String title = testDataRepo.getCurrentPublication().getTitle();
        iSearchFor(title);
    }

    @Then("^I should see (\\d+) search results$")
    public void iShouldSeeSearchResults(String count) throws Throwable {
        if (count.equals("0")) {
            count = "No results";
        }
        assertThat("Correct result count found", searchPage.getResultCount(), startsWith(count));
    }

    @Then("^I should see search results starting with:$")
    public void iShouldSeeSearchResultsStartingWith(DataTable searchResults) throws Throwable {

        List<String> actualResults = searchPage.getSearchResultWidgets()
            .stream()
            .map(SearchResultWidget::getTitle)
            .collect(toList());

        List<Matcher<? super String>> expectedResults = searchResults.asList(String.class)
            .stream()
            .map(Matchers::startsWith)
            .collect(toList());

        assertThat("Search results match", actualResults, contains(expectedResults));
    }
}