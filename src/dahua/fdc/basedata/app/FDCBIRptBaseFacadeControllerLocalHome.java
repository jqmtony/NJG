package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCBIRptBaseFacadeControllerLocalHome extends EJBLocalHome
{
    FDCBIRptBaseFacadeControllerLocal create() throws CreateException;
}