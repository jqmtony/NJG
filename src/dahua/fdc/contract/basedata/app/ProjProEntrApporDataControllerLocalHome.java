package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjProEntrApporDataControllerLocalHome extends EJBLocalHome
{
    ProjProEntrApporDataControllerLocal create() throws CreateException;
}