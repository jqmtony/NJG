package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestTypeFactory
{
    private TestTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.ITestType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ITestType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D17AF2E") ,com.kingdee.eas.port.equipment.base.ITestType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.ITestType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ITestType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D17AF2E") ,com.kingdee.eas.port.equipment.base.ITestType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.ITestType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ITestType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D17AF2E"));
    }
    public static com.kingdee.eas.port.equipment.base.ITestType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ITestType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D17AF2E"));
    }
}