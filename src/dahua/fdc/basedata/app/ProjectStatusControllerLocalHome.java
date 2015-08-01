package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectStatusControllerLocalHome extends EJBLocalHome
{
    ProjectStatusControllerLocal create() throws CreateException;
}