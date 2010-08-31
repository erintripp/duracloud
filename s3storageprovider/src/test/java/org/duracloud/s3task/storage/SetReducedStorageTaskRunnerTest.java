/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.s3task.storage;

import com.amazonaws.services.s3.model.StorageClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author: Bill Branan
 * Date: Aug 30, 2010
 */
public class SetReducedStorageTaskRunnerTest extends SetStorageClassTestBase {

    @Test
    public void testPerformTask() {
        SetReducedStorageTaskRunner runner =
            new SetReducedStorageTaskRunner(s3Provider, s3Client);

        assertEquals("set-reduced-redundancy-storage-class", runner.getName());
        assertEquals(StorageClass.ReducedRedundancy, runner.getStorageClass());

        String result = runner.performTask("spaceId");
        assertNotNull(result);
        assertTrue(result.startsWith("3 items updated successfully"));     
    }

}