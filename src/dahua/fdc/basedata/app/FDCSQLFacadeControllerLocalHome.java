package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSQLFacadeControllerLocalHome extends EJBLocalHome
{
    FDCSQLFacadeControllerLocal create() throws CreateException;
}