/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.duraservice.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.core.Ordered;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.lang.reflect.Method;

public class GetDeployedServicesAdvice
        implements AfterReturningAdvice, Ordered {

    private final Logger log = LoggerFactory.getLogger(GetDeployedServicesAdvice.class);

    private JmsTemplate getDeployedServicesJmsTemplate;

    private Destination destination;

    private int order;

    public void afterReturning(Object returnObj,
                               Method method,
                               Object[] methodArgs,
                               Object targetObj) throws Throwable {

        if (log.isDebugEnabled()) {
            doLogging(returnObj, method, methodArgs, targetObj);
        }

        publishEvent();
    }

    private void publishEvent() {
        getGetDeployedServicesJmsTemplate().convertAndSend(getDestination(), new ServiceMessage());
    }

    private void doLogging(Object returnObj,
                           Method method,
                           Object[] methodArgs,
                           Object targetObj) {
        String pre0 = "--------------------------";
        String pre1 = pre0 + "--";
        String pre2 = pre1 + "--";

        log.debug(pre0 + "advice: publish to getDeployedServices topic");
        if (targetObj != null && targetObj.getClass() != null) {
            log.debug(pre1 + "object: " + targetObj.getClass().getName());
        }
        if (method != null) {
            log.debug(pre1 + "method: " + method.getName());
        }
        if (methodArgs != null) {
            for (Object obj : methodArgs) {
                String argValue;
                if(obj == null) {
                    argValue = "null";
                } else {
                    argValue = obj.toString();
                }
                log.debug(pre2 + "method-arg: " + argValue);
            }
        }
    }

    public JmsTemplate getGetDeployedServicesJmsTemplate() {
        return getDeployedServicesJmsTemplate;
    }

    public void setGetDeployedServicesJmsTemplate(JmsTemplate getDeployedServicesJmsTemplate) {
        this.getDeployedServicesJmsTemplate = getDeployedServicesJmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }    

}