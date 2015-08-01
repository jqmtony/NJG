package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LandDeveloperControllerLocalHome extends EJBLocalHome
{
    LandDeveloperControllerLocal create() throws CreateException;
}