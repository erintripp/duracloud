/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.duraservice.aop;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

import static junit.framework.Assert.assertTrue;

public class GetDeployedAdviceTest {

    private GetDeployedAdvice getDeployedAdvice;

    @Before
    public void setUp() throws Exception {
        getDeployedAdvice = new GetDeployedAdvice();
    }

    @Test
    public void testNullParams() throws Throwable {
        try{
            getDeployedAdvice.afterReturning(null, null, null, null);
            assertTrue(false);

        } catch(NullPointerException npe) {
            assertTrue(true);
        }
    }

    @Test
    public void testEmptyParam() throws Throwable {
        try{
            getDeployedAdvice.afterReturning(null, null, new Object[]{}, null);
            assertTrue(false);

        } catch(ArrayIndexOutOfBoundsException aobe) {
            assertTrue(true);
        }
    }

    @Test
    public void testNullDestination() throws Throwable {
        ServiceMessage msg = new ServiceMessage();
        msg.setServiceId(null);
        msg.setDeploymentId(null);

        JmsTemplate jmsTemplate = EasyMock.createMock("JmsTemplate",
                                                      JmsTemplate.class);
        Destination destination = null;

        jmsTemplate.convertAndSend((Destination)EasyMock.isNull(),
                                   ServiceMessageEquals.eqServiceMessage(msg));
        EasyMock.expectLastCall().once();

        EasyMock.replay(jmsTemplate);

        getDeployedAdvice.setGetDeployedJmsTemplate(jmsTemplate);
        getDeployedAdvice.setDestination(destination);
        getDeployedAdvice.afterReturning(null, null,
                                         new Object[]{null,null}, null);

        EasyMock.verify(jmsTemplate);
    }

    @Test
    public void testMessage() throws Throwable {
        String id = "1";

        ServiceMessage msg = new ServiceMessage();
        msg.setServiceId(id);
        msg.setDeploymentId(id);

        JmsTemplate jmsTemplate = EasyMock.createMock("JmsTemplate",
                                                      JmsTemplate.class);
        Destination destination = EasyMock.createMock("Destination",
                                                      Destination.class);

        jmsTemplate.convertAndSend((Destination)EasyMock.notNull(),
                                   ServiceMessageEquals.eqServiceMessage(msg));
        EasyMock.expectLastCall().once();

        EasyMock.replay(jmsTemplate);

        getDeployedAdvice.setGetDeployedJmsTemplate(jmsTemplate);
        getDeployedAdvice.setDestination(destination);
        getDeployedAdvice.afterReturning(null, null,
                                         new Object[]{new Integer(id),new Integer(id)}, null);

        EasyMock.verify(jmsTemplate);
    }
}