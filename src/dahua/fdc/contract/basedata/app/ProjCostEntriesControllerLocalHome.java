package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjCostEntriesControllerLocalHome extends EJBLocalHome
{
    ProjCostEntriesControllerLocal create() throws CreateException;
}