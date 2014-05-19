package com.kingdee.eas.port.equipment.record;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IEquipment extends IDataBase
{
    public EquipmentInfo getEquipmentInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EquipmentInfo getEquipmentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EquipmentInfo getEquipmentInfo(String oql) throws BOSException, EASBizException;
    public EquipmentCollection getEquipmentCollection() throws BOSException;
    public EquipmentCollection getEquipmentCollection(EntityViewInfo view) throws BOSException;
    public EquipmentCollection getEquipmentCollection(String oql) throws BOSException;
}