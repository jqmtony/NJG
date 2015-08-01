package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotSettEntryControllerLocalHome extends EJBLocalHome
{
    DBDynCostSnapShotSettEntryControllerLocal create() throws CreateException;
}