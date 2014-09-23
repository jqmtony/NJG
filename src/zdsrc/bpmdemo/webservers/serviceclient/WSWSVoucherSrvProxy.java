/**
 * WSWSVoucherSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.kingdee.eas.bpmdemo.webservers.serviceclient;

public interface WSWSVoucherSrvProxy extends java.rmi.Remote {
    public com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSRtnInfo[] importVoucher(com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucher[] voucherCols, boolean isTempSave, boolean isVerify, boolean hasCashflow) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[][] importVoucher(com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucher[] voucherCols, int isVerify, int isImpCashflow) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
    public java.lang.String[][] importVoucherOfReturnID(com.kingdee.eas.bpmdemo.webservers.serviceclient.WSWSVoucher[] voucherCols, int isVerify, int isImpCashflow) throws java.rmi.RemoteException, com.kingdee.eas.bpmdemo.webservers.serviceclient.WSInvokeException;
}
