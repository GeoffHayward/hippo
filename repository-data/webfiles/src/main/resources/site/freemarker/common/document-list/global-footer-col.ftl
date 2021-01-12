<#ftl output_format="HTML">
<#include "../../include/imports.ftl">

<#-- @ftlvariable name="wrappingDocument" type="uk.nhs.digital.website.beans.Calltoaction" -->
<#-- @ftlvariable id="editMode" type="java.lang.Boolean"-->

<@hst.setBundle basename="rb.generic.texts"/>
<@fmt.message key="text.sr-only-link" var="srOnlyLinkText" />

<#assign hasWrappingDoc = wrappingDocument?has_content />
<#assign hasWrappingDocLink = wrappingDocument.external?has_content || wrappingDocument.internal?has_content />

<#assign wrapperId = hasWrappingDoc?then("footer-section-wrapper-${slugify(wrappingDocument.title)}", "footer-section-wrapper") />
<#assign listId = hasWrappingDoc?then("footer-section-${slugify(wrappingDocument.title)}", "footer-section") />

<#assign socialMedia = [] />

<div id="${wrapperId}">
    <#if hasWrappingDoc>
        <div id="${listId}" class="nhsd-t-body-s nhsd-!t-font-weight-bold nhsd-!t-margin-bottom-1">${wrappingDocument.title}</div>
    </#if>
    <ul class="nhsd-t-list nhsd-t-list--links" aria-labelledby="${listId}">
        <#if pageable?? && pageable.items?has_content>
            <#list pageable.items as item>
                <#assign hasLink = item.external?has_content || item.internal?has_content />
                <#assign hasLabel = item.label?has_content />
                <#assign label = hasLabel?then(item.label, item.title) />

                <li class="nhsd-t-body-s">
                    <#if socialMedia>
                        <span class="nhsd-a-icon nhsd-a-icon--size-s nhsd-a-icon--col-dark-grey">
                            <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" aria-hidden="true" focusable="false" viewBox="0 0 16 16"  width="100%" height="100%">
                                <path d="M7,10.9c-2.1,0-3.9-1.7-3.9-3.9c0-2.1,1.7-3.9,3.9-3.9c2.1,0,3.9,1.7,3.9,3.9C10.9,9.2,9.2,10.9,7,10.9zM13.4,14.8l1.4-1.4l-3-3c0.7-1,1.1-2.1,1.1-3.4c0-3.2-2.6-5.8-5.8-5.8C3.8,1.2,1.2,3.8,1.2,7c0,3.2,2.6,5.8,5.8,5.8c1.3,0,2.4-0.4,3.4-1.1L13.4,14.8z"/>
                            </svg>
                        </span>
                    </#if>

                    <#if hasLink>
                        <#if item.internal?has_content>
                            <a class="nhsd-a-link nhsd-a-link--col-dark-grey" href="<@hst.link hippobean=item.internal/>">
                        <#else>
                            <a class="nhsd-a-link nhsd-a-link--col-dark-grey" href="${item.external}" target="_blank" rel="external">
                        </#if>
                    </#if>
                    ${label}
                    <#if item.external?has_content>
                        <span class="nhsd-t-sr-only">${srOnlyLinkText}</span>
                    </#if>
                    <#if hasLink>
                        </a>
                    </#if>
                </li>
            </#list>
        </#if>
    </ul>
</div>
