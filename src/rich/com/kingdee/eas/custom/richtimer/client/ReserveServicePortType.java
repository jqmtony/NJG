/**
 * ReserveServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kingdee.eas.custom.richtimer.client;

public interface ReserveServicePortType extends java.rmi.Remote {
    public java.lang.String openOrgan(java.lang.String organCD, java.lang.String organflag) throws java.rmi.RemoteException;
    public void main(java.lang.String[] args) throws java.rmi.RemoteException;
    public java.lang.String saveCompanyInfo(java.lang.String param) throws java.rmi.RemoteException;
    public java.lang.String syncCompanyPackageInfo(java.lang.String companyCD, java.lang.String organCD) throws java.rmi.RemoteException;
    public java.lang.String beforehandBatch(java.lang.String pid, java.lang.String ldNo, java.lang.String companyID, java.lang.String department, java.lang.String organCD, java.lang.String flag) throws java.rmi.RemoteException;
    public java.lang.String deleteCompanyInfo(java.lang.String dwdm) throws java.rmi.RemoteException;
    public java.lang.String queryJcxx_jcxm(java.lang.String studyid) throws java.rmi.RemoteException;
    public java.lang.String queryJcxx_kshz(java.lang.String studyid) throws java.rmi.RemoteException;
    public java.lang.String syncPackageInfo(java.lang.String packageid, java.lang.String organCD) throws java.rmi.RemoteException;
    public java.lang.String queryCheckinCustomer(java.lang.String studyid, java.lang.String reserveNo, java.lang.String sfzh, java.lang.String phone, java.lang.String xm) throws java.rmi.RemoteException;
    public java.lang.String queryCheckinItem(java.lang.String studyid) throws java.rmi.RemoteException;
    public java.lang.String reserveIssue(java.lang.String organCD, java.lang.String pid, java.lang.String reserveNo, java.lang.String sfzh, java.lang.String phone, java.lang.String xm, java.lang.String cardNo) throws java.rmi.RemoteException;
}
