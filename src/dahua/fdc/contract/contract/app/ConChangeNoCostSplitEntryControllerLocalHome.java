package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConChangeNoCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    ConChangeNoCostSplitEntryControllerLocal create() throws CreateException;
}