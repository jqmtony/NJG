/**
 * BPMServiceForERPSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public interface BPMServiceForERPSoap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String withdraw(java.lang.String btid, java.lang.String boid, java.lang.String procid) throws java.rmi.RemoteException;
}
