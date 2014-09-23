/**
 * WSWSVoucherSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public class WSWSVoucherSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSrvProxyService {

    // Use to get a proxy class for WSWSVoucher
    private final java.lang.String WSWSVoucher_address = "http://10.130.12.34:7888/ormrpc/services/WSWSVoucher";

    public java.lang.String getWSWSVoucherAddress() {
        return WSWSVoucher_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSWSVoucherWSDDServiceName = "WSWSVoucher";

    public java.lang.String getWSWSVoucherWSDDServiceName() {
        return WSWSVoucherWSDDServiceName;
    }

    public void setWSWSVoucherWSDDServiceName(java.lang.String name) {
        WSWSVoucherWSDDServiceName = name;
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSrvProxy getWSWSVoucher() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSWSVoucher_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSWSVoucher(endpoint);
    }

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSrvProxy getWSWSVoucher(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSWSVoucherWSDDServiceName());
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
            if (com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSoapBindingStub _stub = new com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucherSoapBindingStub(new java.net.URL(WSWSVoucher_address), this);
                _stub.setPortName(getWSWSVoucherWSDDServiceName());
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
        if ("WSWSVoucher".equals(inputPortName)) {
            return getWSWSVoucher();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.130.12.34:7888/ormrpc/services/WSWSVoucher", "WSWSVoucherSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("WSWSVoucher"));
        }
        return ports.iterator();
    }

}
