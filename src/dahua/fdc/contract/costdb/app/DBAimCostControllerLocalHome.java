package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBAimCostControllerLocalHome extends EJBLocalHome
{
    DBAimCostControllerLocal create() throws CreateException;
}