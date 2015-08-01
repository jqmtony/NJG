package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCBIRptBaseFacadeControllerRemoteHome extends EJBHome
{
    FDCBIRptBaseFacadeControllerRemote create() throws CreateException, RemoteException;
}