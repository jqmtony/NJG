package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InviteTypeControllerRemoteHome extends EJBHome
{
    InviteTypeControllerRemote create() throws CreateException, RemoteException;
}