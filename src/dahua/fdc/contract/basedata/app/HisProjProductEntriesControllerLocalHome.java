package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HisProjProductEntriesControllerLocalHome extends EJBLocalHome
{
    HisProjProductEntriesControllerLocal create() throws CreateException;
}