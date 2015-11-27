package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TreeNodeFactory
{
    private TreeNodeFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ITreeNode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ITreeNode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("47DFAA47") ,com.kingdee.eas.fdc.gcftbiaoa.ITreeNode.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.ITreeNode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ITreeNode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("47DFAA47") ,com.kingdee.eas.fdc.gcftbiaoa.ITreeNode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ITreeNode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ITreeNode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("47DFAA47"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ITreeNode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ITreeNode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("47DFAA47"));
    }
}