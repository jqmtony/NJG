package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractCodingTypeControllerRemoteHome extends EJBHome
{
    ContractCodingTypeControllerRemote create() throws CreateException, RemoteException;
}