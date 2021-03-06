package uk.nhs.digital.website.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

//import java.util.*;

@HippoEssentialsGenerated(internalName = "website:openinghours")
@Node(jcrType = "website:openinghours")
public class OpeningHours extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "website:monday")
    public String getMonday() {
        return getSingleProperty("website:monday");
    }

    @HippoEssentialsGenerated(internalName = "website:tuesday")
    public String getTuesday() {
        return getSingleProperty("website:tuesday");
    }

    @HippoEssentialsGenerated(internalName = "website:wednesday")
    public String getWednesday() {
        return getSingleProperty("website:wednesday");
    }

    @HippoEssentialsGenerated(internalName = "website:thursday")
    public String getThursday() {
        return getSingleProperty("website:thursday");
    }

    @HippoEssentialsGenerated(internalName = "website:friday")
    public String getFriday() {
        return getSingleProperty("website:friday");
    }

    @HippoEssentialsGenerated(internalName = "website:saturday")
    public String getSaturday() {
        return getSingleProperty("website:saturday");
    }

    @HippoEssentialsGenerated(internalName = "website:sunday")
    public String getSunday() {
        return getSingleProperty("website:sunday");
    }

    public String getSectionType() {
        return "openinghours";
    }

}
