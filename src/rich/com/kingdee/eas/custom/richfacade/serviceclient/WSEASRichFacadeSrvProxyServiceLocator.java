/**
 * WSEASRichFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingdee.eas.custom.richfacade.serviceclient;

public class WSEASRichFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxyService {

    public WSEASRichFacadeSrvProxyServiceLocator() {
    }


    public WSEASRichFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSEASRichFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSEASRichFacade
    private java.lang.String WSEASRichFacade_address = "http://127.0.0.1:56898/ormrpc/services/WSEASRichFacade";

    public java.lang.String getWSEASRichFacadeAddress() {
        return WSEASRichFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSEASRichFacadeWSDDServiceName = "WSEASRichFacade";

    public java.lang.String getWSEASRichFacadeWSDDServiceName() {
        return WSEASRichFacadeWSDDServiceName;
    }

    public void setWSEASRichFacadeWSDDServiceName(java.lang.String name) {
        WSEASRichFacadeWSDDServiceName = name;
    }

    public com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxy getWSEASRichFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSEASRichFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSEASRichFacade(endpoint);
    }

    public com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxy getWSEASRichFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSoapBindingStub _stub = new com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSEASRichFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSEASRichFacadeEndpointAddress(java.lang.String address) {
        WSEASRichFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSoapBindingStub _stub = new com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSoapBindingStub(new java.net.URL(WSEASRichFacade_address), this);
                _stub.setPortName(getWSEASRichFacadeWSDDServiceName());
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
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSEASRichFacade".equals(inputPortName)) {
            return getWSEASRichFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSEASRichFacade", "WSEASRichFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSEASRichFacade", "WSEASRichFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSEASRichFacade".equals(portName)) {
            setWSEASRichFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
