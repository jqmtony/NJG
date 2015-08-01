package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface JobTypeControllerLocalHome extends EJBLocalHome
{
    JobTypeControllerLocal create() throws CreateException;
}