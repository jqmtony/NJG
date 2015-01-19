/**
 * BPMServiceForERPLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public class BPMServiceForERPLocator extends org.apache.axis.client.Service implements com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERP {

    // Use to get a proxy class for BPMServiceForERPSoap
    private final java.lang.String BPMServiceForERPSoap_address = "http://10.130.12.20/services/BPMServiceForERP.asmx";
	//private final java.lang.String BPMServiceForERPSoap_address = "http://bpm.cpmlg.net/Services/BPMServiceForERP.asmx";
    public java.lang.String getBPMServiceForERPSoapAddress() {
        return BPMServiceForERPSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BPMServiceForERPSoapWSDDServiceName = "BPMServiceForERPSoap";

    public java.lang.String getBPMServiceForERPSoapWSDDServiceName() {
        return BPMServiceForERPSoapWSDDServiceName;
    }

    public void setBPMServiceForERPSoapWSDDServiceName(java.lang.String name) {
        BPMServiceForERPSoapWSDDServiceName = name;
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap getBPMServiceForERPSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BPMServiceForERPSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBPMServiceForERPSoap(endpoint);
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap getBPMServiceForERPSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoapStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoapStub(portAddress, this);
            _stub.setPortName(getBPMServiceForERPSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }


    // Use to get a proxy class for BPMServiceForERPSoap12
    private final java.lang.String BPMServiceForERPSoap12_address = "http://10.130.12.20/services/BPMServiceForERP.asmx";
    //private final java.lang.String BPMServiceForERPSoap12_address = "http://bpm.cpmlg.net/Services/BPMServiceForERP.asmx"; 
    
    public java.lang.String getBPMServiceForERPSoap12Address() {
        return BPMServiceForERPSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BPMServiceForERPSoap12WSDDServiceName = "BPMServiceForERPSoap12";

    public java.lang.String getBPMServiceForERPSoap12WSDDServiceName() {
        return BPMServiceForERPSoap12WSDDServiceName;
    }

    public void setBPMServiceForERPSoap12WSDDServiceName(java.lang.String name) {
        BPMServiceForERPSoap12WSDDServiceName = name;
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap getBPMServiceForERPSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BPMServiceForERPSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBPMServiceForERPSoap12(endpoint);
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap getBPMServiceForERPSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap12Stub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap12Stub(portAddress, this);
            _stub.setPortName(getBPMServiceForERPSoap12WSDDServiceName());
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
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoapStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoapStub(new java.net.URL(BPMServiceForERPSoap_address), this);
                _stub.setPortName(getBPMServiceForERPSoapWSDDServiceName());
                return _stub;
            }
            if (com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap12Stub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap12Stub(new java.net.URL(BPMServiceForERPSoap12_address), this);
                _stub.setPortName(getBPMServiceForERPSoap12WSDDServiceName());
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
        if ("BPMServiceForERPSoap".equals(inputPortName)) {
            return getBPMServiceForERPSoap();
        }
        else if ("BPMServiceForERPSoap12".equals(inputPortName)) {
            return getBPMServiceForERPSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "BPMServiceForERP");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("BPMServiceForERPSoap"));
            ports.add(new javax.xml.namespace.QName("BPMServiceForERPSoap12"));
        }
        return ports.iterator();
    }

}
