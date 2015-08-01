package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InviteTypeControllerLocalHome extends EJBLocalHome
{
    InviteTypeControllerLocal create() throws CreateException;
}