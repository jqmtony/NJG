package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ClearSplitFacadeControllerLocalHome extends EJBLocalHome
{
    ClearSplitFacadeControllerLocal create() throws CreateException;
}