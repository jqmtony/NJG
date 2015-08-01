package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface WatcherControllerLocalHome extends EJBLocalHome
{
    WatcherControllerLocal create() throws CreateException;
}