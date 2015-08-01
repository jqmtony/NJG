package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettNoCostSplitControllerLocalHome extends EJBLocalHome
{
    SettNoCostSplitControllerLocal create() throws CreateException;
}