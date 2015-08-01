package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectFacadeControllerLocalHome extends EJBLocalHome
{
    ProjectFacadeControllerLocal create() throws CreateException;
}