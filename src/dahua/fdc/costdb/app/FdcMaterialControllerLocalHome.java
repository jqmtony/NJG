package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FdcMaterialControllerLocalHome extends EJBLocalHome
{
    FdcMaterialControllerLocal create() throws CreateException;
}