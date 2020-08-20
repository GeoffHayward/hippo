package uk.nhs.digital.apispecs.swagger.request.bodyextractor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import uk.nhs.digital.test.util.ReflectionTestUtils;

import java.util.List;

public class RequestBodyTest {

    @Test
    public void getMediaTypes_returnsMediaTypeObjects_enrichedWithNamesFromTheInternalMapKeys() {

        // given
        final String mediaType1 = "application/json";
        final RequestBodyMediaTypeObject expectedMediaTypeObject1 = mock(RequestBodyMediaTypeObject.class);

        final String mediaType2 = "application/xml";
        final RequestBodyMediaTypeObject expectedMediaTypeObject2 = mock(RequestBodyMediaTypeObject.class);

        final RequestBodyMediaTypeObjects mediaTypes = new RequestBodyMediaTypeObjects();
        mediaTypes.put(mediaType1, expectedMediaTypeObject1);
        mediaTypes.put(mediaType2, expectedMediaTypeObject2);

        final RequestBody requestBody = new RequestBody();
        ReflectionTestUtils.setField(requestBody, "content", mediaTypes);

        // when
        final List<RequestBodyMediaTypeObject> actualRequestBodyMediaTypes = requestBody.getMediaTypes();

        // then
        assertThat("Returns all media type objects from the internal map.", actualRequestBodyMediaTypes.size(), is(2));
        final RequestBodyMediaTypeObject actualMediaTypeObject1 = actualRequestBodyMediaTypes.get(0);
        assertThat("First media type object comes is a value from the internal map",
            actualMediaTypeObject1, sameInstance(expectedMediaTypeObject1)
        );
        then(actualMediaTypeObject1).should().setName(mediaType1);
        verifyNoMoreInteractions(actualMediaTypeObject1);

        final RequestBodyMediaTypeObject actualMediaTypeObject2 = actualRequestBodyMediaTypes.get(1);
        assertThat("Second media type object comes is a value from the internal map",
            actualMediaTypeObject2, sameInstance(expectedMediaTypeObject2)
        );
        then(actualMediaTypeObject2).should().setName(mediaType2);
        verifyNoMoreInteractions(actualMediaTypeObject2);
    }
}
