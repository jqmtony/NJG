package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NodeMeasureEntryFactory
{
    private NodeMeasureEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasureEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasureEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C97D911") ,com.kingdee.eas.fdc.contract.INodeMeasureEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.INodeMeasureEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasureEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C97D911") ,com.kingdee.eas.fdc.contract.INodeMeasureEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasureEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasureEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C97D911"));
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasureEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasureEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C97D911"));
    }
}