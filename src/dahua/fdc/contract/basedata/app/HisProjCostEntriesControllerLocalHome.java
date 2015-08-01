package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HisProjCostEntriesControllerLocalHome extends EJBLocalHome
{
    HisProjCostEntriesControllerLocal create() throws CreateException;
}