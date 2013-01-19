/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.glacierstorage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.StorageClass;
import org.duracloud.s3storage.S3StorageProvider;
import org.duracloud.storage.error.NotFoundException;
import org.duracloud.storage.error.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides content storage backed by Amazon's Glacier storage system.
 *
 * @author Bill Branan
 * Date: Dec 6, 2012
 */
public class GlacierStorageProvider extends S3StorageProvider {

    private final Logger log =
        LoggerFactory.getLogger(GlacierStorageProvider.class);

    public GlacierStorageProvider(String accessKey, String secretKey) {
        super(accessKey, secretKey);
    }

    public GlacierStorageProvider(AmazonS3Client s3Client, String accessKey) {
        super(s3Client, accessKey, null);
    }

    /**
     * {@inheritDoc}
     *
     * Performs the usual space creation activities, then sets up lifecycle
     * policies on the S3 bucket which indicate that all content should be
     * archived in AWS Glacier as quickly as possible after it lands in the
     * bucket.
     */
    @Override
    public void createSpace(String spaceId) {
        log.debug("createSpace(" + spaceId + ")");

        super.createSpace(spaceId);

        // Define the transition to Glacier
        BucketLifecycleConfiguration.Transition transition =
            new BucketLifecycleConfiguration.Transition()
                .withDays(0)
                .withStorageClass(StorageClass.Glacier);

        // Use the transition in a rule
        BucketLifecycleConfiguration.Rule rule =
            new BucketLifecycleConfiguration.Rule()
                .withId("Archive all files")
                .withPrefix("")
                .withTransition(transition)
                .withStatus(BucketLifecycleConfiguration.ENABLED.toString());

        // Create lifecycle config with rule
        List<BucketLifecycleConfiguration.Rule> rules = new ArrayList<>();
        rules.add(rule);

        BucketLifecycleConfiguration config =
            new BucketLifecycleConfiguration()
                .withRules(rules);

        // Apply lifecycle config to bucket
        setNewSpaceLifecycle(spaceId, config);
    }

    protected void setNewSpaceLifecycle(String spaceId,
                                        BucketLifecycleConfiguration config) {
        boolean success = false;
        int maxLoops = 6;
        for (int loops = 0; !success && loops < maxLoops; loops++) {
            try {
                s3Client.setBucketLifecycleConfiguration(getBucketName(spaceId),
                                                         config);
                success = true;
            } catch (NotFoundException e) {
                success = false;
            }
        }

        if (!success) {
            throw new StorageException(
                "Lifecycle policy (to use Glacier) for space " + spaceId +
                " could not be applied. The space cannot be found.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String copyContent(String sourceSpaceId,
                              String sourceContentId,
                              String destSpaceId,
                              String destContentId) {
        try {
            return super.copyContent(sourceSpaceId,
                                     sourceContentId,
                                     destSpaceId,
                                     destContentId);
        } catch (StorageException e) {
            // TODO: Handle attempts to copy archived content
            throw e;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getContent(String spaceId, String contentId) {
        log.debug("getContent(" + spaceId + ", " + contentId + ")");

        try {
            return super.getContent(spaceId, contentId);
        } catch (StorageException e) {
            // TODO: Handle attempts to retrieve archived content
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentProperties(String spaceId,
                                     String contentId,
                                     Map<String, String> contentProperties) {
        log.debug("setContentProperties(" + spaceId + ", " + contentId + ")");

        try {
            super.setContentProperties(spaceId,
                                       contentId,
                                       contentProperties);
        } catch (StorageException e) {
            // TODO: Handle attempts to set properties on archived content
            throw e; 
        }
    }

}