package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaySplitUtilFacadeControllerLocalHome extends EJBLocalHome
{
    PaySplitUtilFacadeControllerLocal create() throws CreateException;
}