package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBCostEntryControllerLocalHome extends EJBLocalHome
{
    DBCostEntryControllerLocal create() throws CreateException;
}