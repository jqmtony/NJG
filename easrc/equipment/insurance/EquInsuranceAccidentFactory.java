package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquInsuranceAccidentFactory
{
    private EquInsuranceAccidentFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85931329") ,com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident.class);
    }
    
    public static com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85931329") ,com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85931329"));
    }
    public static com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85931329"));
    }
}