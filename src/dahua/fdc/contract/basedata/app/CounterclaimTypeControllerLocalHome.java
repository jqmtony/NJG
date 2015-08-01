package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CounterclaimTypeControllerLocalHome extends EJBLocalHome
{
    CounterclaimTypeControllerLocal create() throws CreateException;
}