/**
 * WSgetInfoFacadeSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public interface WSgetInfoFacadeSrvProxy extends java.rmi.Remote {
    public java.lang.String[] approveClose(java.lang.String strBSID, java.lang.String strBOID, int iProcInstID, java.lang.String eProcessInstanceResult, java.lang.String strComment, java.lang.String dtTime) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] getrRelatedBillInfo(java.lang.String strBTID, java.lang.String strBOID, java.lang.String strRelatedCode) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] getcurProject() throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] submitResult(java.lang.String strBSID, java.lang.String strBOID, boolean bSuccess, int iProcInstID, java.lang.String procURL, java.lang.String strMessage) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] getpoint(java.lang.String pointID) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] approveBack(java.lang.String strBTID, java.lang.String strBOID, java.lang.String strXML) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] getbillInfo(java.lang.String strBSID, java.lang.String strBOID) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[] getDemo() throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
}
