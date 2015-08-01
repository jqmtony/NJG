package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostAccountWithAccountControllerLocalHome extends EJBLocalHome
{
    CostAccountWithAccountControllerLocal create() throws CreateException;
}