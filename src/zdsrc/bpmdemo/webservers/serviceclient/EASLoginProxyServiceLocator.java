/**
 * EASLoginProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public class EASLoginProxyServiceLocator extends org.apache.axis.client.Service implements com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxyService {

    // Use to get a proxy class for EASLogin
    private final java.lang.String EASLogin_address = "http://10.130.12.34:7888/ormrpc/services/EASLogin";

    public java.lang.String getEASLoginAddress() {
        return EASLogin_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EASLoginWSDDServiceName = "EASLogin";

    public java.lang.String getEASLoginWSDDServiceName() {
        return EASLoginWSDDServiceName;
    }

    public void setEASLoginWSDDServiceName(java.lang.String name) {
        EASLoginWSDDServiceName = name;
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EASLogin_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEASLogin(endpoint);
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginSoapBindingStub(portAddress, this);
            _stub.setPortName(getEASLoginWSDDServiceName());
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
            if (com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginSoapBindingStub(new java.net.URL(EASLogin_address), this);
                _stub.setPortName(getEASLoginWSDDServiceName());
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
        if ("EASLogin".equals(inputPortName)) {
            return getEASLogin();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.130.12.34:7888/ormrpc/services/EASLogin", "EASLoginProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("EASLogin"));
        }
        return ports.iterator();
    }

}
