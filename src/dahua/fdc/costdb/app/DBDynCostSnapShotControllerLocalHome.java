package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotControllerLocalHome extends EJBLocalHome
{
    DBDynCostSnapShotControllerLocal create() throws CreateException;
}