/**
 * ReserveServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingdee.eas.custom.richtimer.client;

public class ReserveServiceLocator extends org.apache.axis.client.Service implements com.kingdee.eas.custom.richtimer.client.ReserveService {

    public ReserveServiceLocator() {
    }


    public ReserveServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReserveServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for reserveServiceHttpSoap12Endpoint
    private java.lang.String reserveServiceHttpSoap12Endpoint_address = "http://172.3.1.203:8188/richTimer/services/reserveService.reserveServiceHttpSoap12Endpoint/";

    public java.lang.String getreserveServiceHttpSoap12EndpointAddress() {
        return reserveServiceHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String reserveServiceHttpSoap12EndpointWSDDServiceName = "reserveServiceHttpSoap12Endpoint";

    public java.lang.String getreserveServiceHttpSoap12EndpointWSDDServiceName() {
        return reserveServiceHttpSoap12EndpointWSDDServiceName;
    }

    public void setreserveServiceHttpSoap12EndpointWSDDServiceName(java.lang.String name) {
        reserveServiceHttpSoap12EndpointWSDDServiceName = name;
    }

    public com.kingdee.eas.custom.richtimer.client.ReserveServicePortType getreserveServiceHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(reserveServiceHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getreserveServiceHttpSoap12Endpoint(endpoint);
    }

    public com.kingdee.eas.custom.richtimer.client.ReserveServicePortType getreserveServiceHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap12BindingStub _stub = new com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap12BindingStub(portAddress, this);
            _stub.setPortName(getreserveServiceHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setreserveServiceHttpSoap12EndpointEndpointAddress(java.lang.String address) {
        reserveServiceHttpSoap12Endpoint_address = address;
    }


    // Use to get a proxy class for reserveServiceHttpSoap11Endpoint
    private java.lang.String reserveServiceHttpSoap11Endpoint_address = "http://172.3.1.203:8188/richTimer/services/reserveService.reserveServiceHttpSoap11Endpoint/";

    public java.lang.String getreserveServiceHttpSoap11EndpointAddress() {
        return reserveServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String reserveServiceHttpSoap11EndpointWSDDServiceName = "reserveServiceHttpSoap11Endpoint";

    public java.lang.String getreserveServiceHttpSoap11EndpointWSDDServiceName() {
        return reserveServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setreserveServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        reserveServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.kingdee.eas.custom.richtimer.client.ReserveServicePortType getreserveServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(reserveServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getreserveServiceHttpSoap11Endpoint(endpoint);
    }

    public com.kingdee.eas.custom.richtimer.client.ReserveServicePortType getreserveServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap11BindingStub _stub = new com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getreserveServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setreserveServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        reserveServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kingdee.eas.custom.richtimer.client.ReserveServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap12BindingStub _stub = new com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap12BindingStub(new java.net.URL(reserveServiceHttpSoap12Endpoint_address), this);
                _stub.setPortName(getreserveServiceHttpSoap12EndpointWSDDServiceName());
                return _stub;
            }
            if (com.kingdee.eas.custom.richtimer.client.ReserveServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap11BindingStub _stub = new com.kingdee.eas.custom.richtimer.client.ReserveServiceSoap11BindingStub(new java.net.URL(reserveServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getreserveServiceHttpSoap11EndpointWSDDServiceName());
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
        if ("reserveServiceHttpSoap12Endpoint".equals(inputPortName)) {
            return getreserveServiceHttpSoap12Endpoint();
        }
        else if ("reserveServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getreserveServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.richTimer.rich.com", "reserveService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.richTimer.rich.com", "reserveServiceHttpSoap12Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://webservice.richTimer.rich.com", "reserveServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("reserveServiceHttpSoap12Endpoint".equals(portName)) {
            setreserveServiceHttpSoap12EndpointEndpointAddress(address);
        }
        else 
if ("reserveServiceHttpSoap11Endpoint".equals(portName)) {
            setreserveServiceHttpSoap11EndpointEndpointAddress(address);
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
