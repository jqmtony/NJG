package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IPhotoAudit extends ICoreBillBase
{
    public PhotoAuditCollection getPhotoAuditCollection() throws BOSException;
    public PhotoAuditCollection getPhotoAuditCollection(EntityViewInfo view) throws BOSException;
    public PhotoAuditCollection getPhotoAuditCollection(String oql) throws BOSException;
    public PhotoAuditInfo getPhotoAuditInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PhotoAuditInfo getPhotoAuditInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PhotoAuditInfo getPhotoAuditInfo(String oql) throws BOSException, EASBizException;
    public void audit(PhotoAuditInfo model) throws BOSException;
    public void unAudit(PhotoAuditInfo model) throws BOSException;
}