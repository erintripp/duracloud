/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.s3storageprovider.dto;

import org.duracloud.common.json.JaxbJsonSerializer;
import org.duracloud.error.TaskDataException;

import javax.xml.bind.annotation.XmlValue;
import java.io.IOException;

/**
 * @author Bill Branan
 *         Date: 3/9/15
 */
public class GetSignedUrlTaskResult {

    /**
     * The securely signed streaming URL
     */
    @XmlValue
    private String signedUrl;

    // Required by JAXB
    public GetSignedUrlTaskResult() {}

    public GetSignedUrlTaskResult(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    /**
     * Creates a serialized version of task results
     *
     * @return JSON formatted task result info
     */
    public String serialize() {
        JaxbJsonSerializer<GetSignedUrlTaskResult> serializer =
            new JaxbJsonSerializer<>(GetSignedUrlTaskResult.class);
        try {
            return serializer.serialize(this);
        } catch(IOException e) {
            throw new TaskDataException(
                "Unable to create task result due to: " + e.getMessage());
        }
    }

    /**
     * Parses properties from task result
     *
     * @param taskResult - JSON formatted set of properties
     */
    public static GetSignedUrlTaskResult deserialize(String taskResult) {
        JaxbJsonSerializer<GetSignedUrlTaskResult> serializer =
            new JaxbJsonSerializer<>(GetSignedUrlTaskResult.class);
        try {
            return serializer.deserialize(taskResult);
        } catch(IOException e) {
            throw new TaskDataException(
                "Unable to create task result due to: " + e.getMessage());
        }
    }
    
}
