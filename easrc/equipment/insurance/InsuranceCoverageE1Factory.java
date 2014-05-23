package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceCoverageE1Factory
{
    private InsuranceCoverageE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("64E10F65") ,com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("64E10F65") ,com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("64E10F65"));
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("64E10F65"));
    }
}