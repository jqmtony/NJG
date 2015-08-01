package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotProTypEntryControllerLocalHome extends EJBLocalHome
{
    DBDynCostSnapShotProTypEntryControllerLocal create() throws CreateException;
}