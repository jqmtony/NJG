package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeTypeControllerLocalHome extends EJBLocalHome
{
    ChangeTypeControllerLocal create() throws CreateException;
}