package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RightManagerControllerLocalHome extends EJBLocalHome
{
    RightManagerControllerLocal create() throws CreateException;
}