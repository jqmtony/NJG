package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectIndexDataRptFacadeControllerLocalHome extends EJBLocalHome
{
    ProjectIndexDataRptFacadeControllerLocal create() throws CreateException;
}