/**
 * WSgetInfoFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public class WSgetInfoFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxyService {

    // Use to get a proxy class for WSgetInfoFacade
    private final java.lang.String WSgetInfoFacade_address = "http://10.130.12.34:7888/ormrpc/services/WSgetInfoFacade";

    public java.lang.String getWSgetInfoFacadeAddress() {
        return WSgetInfoFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSgetInfoFacadeWSDDServiceName = "WSgetInfoFacade";

    public java.lang.String getWSgetInfoFacadeWSDDServiceName() {
        return WSgetInfoFacadeWSDDServiceName;
    }

    public void setWSgetInfoFacadeWSDDServiceName(java.lang.String name) {
        WSgetInfoFacadeWSDDServiceName = name;
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy getWSgetInfoFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSgetInfoFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSgetInfoFacade(endpoint);
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy getWSgetInfoFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSgetInfoFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSoapBindingStub(new java.net.URL(WSgetInfoFacade_address), this);
                _stub.setPortName(getWSgetInfoFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("WSgetInfoFacade".equals(inputPortName)) {
            return getWSgetInfoFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.130.12.34:7888/ormrpc/services/WSgetInfoFacade", "WSgetInfoFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("WSgetInfoFacade"));
        }
        return ports.iterator();
    }

}
