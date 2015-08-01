package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettNoCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    SettNoCostSplitEntryControllerLocal create() throws CreateException;
}