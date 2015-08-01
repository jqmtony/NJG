package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CurProjCostEntriesControllerLocalHome extends EJBLocalHome
{
    CurProjCostEntriesControllerLocal create() throws CreateException;
}