/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.duraservice.aop;

public class GetDeployedAdviceTest extends AdviceTestBase {

    @Override
    protected ServiceAdvice getAdvice() {
        return new GetDeployedAdvice();
    }

}
