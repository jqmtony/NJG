package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface UnitDataManagerControllerLocalHome extends EJBLocalHome
{
    UnitDataManagerControllerLocal create() throws CreateException;
}