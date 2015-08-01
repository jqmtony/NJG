package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractDetailDefControllerRemoteHome extends EJBHome
{
    ContractDetailDefControllerRemote create() throws CreateException, RemoteException;
}