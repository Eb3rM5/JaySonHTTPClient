package dev.mainardes.util.httpclient.util;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;

public final class ResponseUtils {

    public static boolean isContentType(String contentType, ContentType expectedContentType){
        return ContentType.parse(contentType).isSameMimeType(expectedContentType);
    }

    public static boolean isExpectedStatus(int status, HttpResponse response){
        return (status == -1 || response.getCode() == status);
    }

    public static boolean isExpectedContentType(ContentType expectedContentType, ClassicHttpResponse response){
        return response.getEntity() != null
                        && (expectedContentType == null
                                || isContentType(response.getEntity().getContentType(), expectedContentType));
    }

    public static boolean isExpectedResponse(int expectedStatus, ContentType expectedContentType, ClassicHttpResponse response){
        return response != null
                    && isExpectedStatus(expectedStatus, response)
                        && isExpectedContentType(expectedContentType, response);
    }

    private ResponseUtils() {}

}
