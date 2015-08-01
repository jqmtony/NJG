package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AdjustTypeControllerLocalHome extends EJBLocalHome
{
    AdjustTypeControllerLocal create() throws CreateException;
}