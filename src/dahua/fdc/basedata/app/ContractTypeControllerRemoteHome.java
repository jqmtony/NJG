package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractTypeControllerRemoteHome extends EJBHome
{
    ContractTypeControllerRemote create() throws CreateException, RemoteException;
}