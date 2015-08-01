package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductTypeControllerLocalHome extends EJBLocalHome
{
    DeductTypeControllerLocal create() throws CreateException;
}