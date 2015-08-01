package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractBillContractPlanControllerRemoteHome extends EJBHome
{
    ContractBillContractPlanControllerRemote create() throws CreateException, RemoteException;
}