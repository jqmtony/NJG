package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CurProjectControllerLocalHome extends EJBLocalHome
{
    CurProjectControllerLocal create() throws CreateException;
}