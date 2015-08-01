package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SelfDirectorControllerLocalHome extends EJBLocalHome
{
    SelfDirectorControllerLocal create() throws CreateException;
}