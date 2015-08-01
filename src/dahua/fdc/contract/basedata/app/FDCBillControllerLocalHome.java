package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCBillControllerLocalHome extends EJBLocalHome
{
    FDCBillControllerLocal create() throws CreateException;
}