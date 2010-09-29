/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.duraservice.rest;

import org.duracloud.common.util.SerializationUtil;
import org.duracloud.duraservice.error.NoSuchDeployedServiceException;
import org.duracloud.duraservice.error.NoSuchServiceComputeInstanceException;
import org.duracloud.duraservice.error.NoSuchServiceException;
import org.duracloud.duraservice.mgmt.ServiceManager;
import org.duracloud.serviceconfig.ServiceInfo;
import org.duracloud.serviceconfig.ServicesConfigDocument;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Provides interaction with content
 *
 * @author Bill Branan
 */
public class ServiceResourceImpl implements ServiceResource {

    private ServiceManager serviceManager;

    public ServiceResourceImpl(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public void configureManager(InputStream configXml) {
        serviceManager.configure(configXml);
    }

    @Override
    public String getDeployedServices() {
        List<ServiceInfo> deployedServices = serviceManager.getDeployedServices();
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        return configDoc.getServiceListAsXML(deployedServices);
    }

    @Override
    public String getAvailableServices() {
        List<ServiceInfo> availableServices = serviceManager.getAvailableServices();
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        return configDoc.getServiceListAsXML(availableServices);
    }

    @Override
    public String getService(int serviceId)
        throws NoSuchServiceException {
        ServiceInfo service = serviceManager.getService(serviceId);
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        return configDoc.getServiceAsXML(service);
    }

    @Override
    public String getDeployedService(int serviceId, int deploymentId)
        throws NoSuchDeployedServiceException {
        ServiceInfo service =
            serviceManager.getDeployedService(serviceId, deploymentId);
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        return configDoc.getServiceAsXML(service);
    }

    @Override
    public String getDeployedServiceProps(int serviceId,
                                                 int deploymentId)
        throws NoSuchDeployedServiceException {
        Map<String, String> serviceProperties =
            serviceManager.getDeployedServiceProps(serviceId, deploymentId);

        //TODO: Update to use a more well structure serialization
        return SerializationUtil.serializeMap(serviceProperties);
    }

    @Override
    public int deployService(int serviceId,
                                     String serviceHost,
                                     InputStream serviceXml)
        throws NoSuchServiceException, NoSuchServiceComputeInstanceException {
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        ServiceInfo service = configDoc.getService(serviceXml);
        return serviceManager.deployService(serviceId,
                                            serviceHost,
                                            service.getUserConfigVersion(),
                                            service.getUserConfigs());
    }

    @Override
    public void updateServiceConfig(int serviceId,
                                           int deploymentId,
                                           InputStream serviceXml)
        throws NoSuchDeployedServiceException {
        ServicesConfigDocument configDoc = new ServicesConfigDocument();
        ServiceInfo service = configDoc.getService(serviceXml);
        serviceManager.updateServiceConfig(serviceId,
                                           deploymentId,
                                           service.getUserConfigVersion(),
                                           service.getUserConfigs());
    }

    @Override
    public void undeployService(int serviceId, int deploymentId)
        throws NoSuchDeployedServiceException {
        serviceManager.undeployService(serviceId, deploymentId);
    }

}