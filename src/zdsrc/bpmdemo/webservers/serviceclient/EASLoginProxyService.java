/**
 * EASLoginProxyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public interface EASLoginProxyService extends javax.xml.rpc.Service {
    public java.lang.String getEASLoginAddress();

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException;

    public com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
