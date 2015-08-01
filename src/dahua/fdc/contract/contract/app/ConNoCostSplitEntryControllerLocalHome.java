package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConNoCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    ConNoCostSplitEntryControllerLocal create() throws CreateException;
}