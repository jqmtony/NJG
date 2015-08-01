package com.kingdee.eas.fdc.basedata.app;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.HisProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.HisProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.HisProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.HisProjectFactory;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.IHisProjProductEntries;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.util.FilterUtility;
/**
 * ����:��ʷ������Ŀ
 * @author jackwang  date:2006-7-20 <p>
 * @version EAS5.1
 */
public class HisProjectControllerBean extends AbstractHisProjectControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.HisProjectControllerBean");
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK objectPK = null;
		HisProjectInfo hisProjectInfo = (HisProjectInfo)model;
//		HisProjectFactory.getLocalInstance(ctx).get
//		//�Զ����ɰ汾��
		
		 
		
		
		
		objectPK = super._addnew(ctx, model);
		//����в�Ʒ��¼,����֮
		if(((HisProjectInfo)model).getHisProjProductEntries()!=null){
			IHisProjProductEntries iHisProjProductEntries  = HisProjProductEntriesFactory.getLocalInstance(ctx);  
			HisProjProductEntriesCollection hisProjProductEntriesCollection = new HisProjProductEntriesCollection();
			hisProjProductEntriesCollection = ((HisProjectInfo)model).getHisProjProductEntries();
			Iterator iteratorEntry = hisProjProductEntriesCollection.iterator();
			HisProjProductEntriesInfo hisProjProductEntriesInfo; 
			 
			hisProjectInfo.put("id", objectPK.getKeyValue("id"));
			while (iteratorEntry.hasNext()) {
				hisProjProductEntriesInfo = (HisProjProductEntriesInfo)iteratorEntry.next();
				hisProjProductEntriesInfo.setHisProject(hisProjectInfo);
				iHisProjProductEntries.addnew(hisProjProductEntriesInfo);				
			}
			
		}			
		return objectPK;
	}
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
    	super._update(ctx, pk, model);
    }
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
    	IHisProjProductEntries iHisProjProductEntries = HisProjProductEntriesFactory.getLocalInstance(ctx);
		iHisProjProductEntries.delete("where hisProject.id = '" + pk + "'");
        super._delete(ctx, pk);
    } 
    //override becuase only node has the same parent can't have same number
    /**
     * ��ͬ�ĸ��ڵ��²�����ͬ�ı��롣
     * @param ctx Context
     * @param model DataBaseInfo
     * @throws BOSException
     * @throws EASBizException
     */
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {
    	//��������ʷ�汾ͬʱ����,���������ظ�У��
//    	checkVersionNameDup(ctx,model);
    }
    /**
     * Ĭ��ʵ�ֶ����� ͬ�ĳ����룬��ID��ͬ���д�������ɰ���Ҫ����ʵ�֡�
     * ע���汾������Ҫ������
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		//������ݿ������ͬlongNumber��ID��ͬ�����ݣ����쳣��
//		FilterInfo lNfilter = new FilterInfo();
//		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber,treeBaseInfo.getLongNumber()));
//		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID,treeBaseInfo.getId().toString(),CompareType.NOTEQUALS));
//		lNfilter.setMaskString("#0 AND #1");
//		
//        //CU����
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeBaseInfo);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            lNfilter.mergeFilter(filterCU,"AND");
//        }
//        
//		
//		if(exists(ctx,lNfilter))
//		{
//			 throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
//		            new Object[]
//		            {treeBaseInfo.getNumber()});
//		}
	}
    /**
    *
    * @param ctx Context
    * @param model DataBaseInfo
    * @throws BOSException
    * @throws EASBizException
    */
   private void checkVersionNameDup(Context ctx , IObjectValue model) throws
       BOSException , EASBizException
   {
       DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
       FilterInfo filter = new FilterInfo();
       FilterItemInfo filterItem = new FilterItemInfo("versionName" ,((HisProjectInfo)dataBaseInfo).getVersionName() ,
           CompareType.EQUALS);
       filter.getFilterItems().add(filterItem);
       if (dataBaseInfo.getId() != null)
       {
           filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                           dataBaseInfo.getId() ,
                                           CompareType.NOTEQUALS);
           filter.getFilterItems().add(filterItem);
           filter.setMaskString("#0 and #1");
       }
//       if(getControlType(ctx,dataBaseInfo).equals(""))
//       {
//	        //�ϲ�Filter������CU���롣
//           FilterInfo filterCU = getFilterForDefaultCU(ctx,model); 
//	        //if(this.getFilterForCheckNumber(model) != null && this.getFilterForCheckNumber(model).size()>0)
//           if(FilterUtility.hasFilterItem(filterCU))
//	        {
//	            filter.mergeFilter(filterCU,"AND");
//	        }
//       }

       EntityViewInfo view = new EntityViewInfo();
       view.setFilter(filter);
       SorterItemCollection sorter = new SorterItemCollection();
       sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
       //@todo ����getPKListЧ�ʺܵͣ�ʹ��exists()�����
       //IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

       //if (pks.length > 0)
       if (super._exists(ctx,filter))
       {
           String name = this._getPropertyAlias(ctx,dataBaseInfo,"versionName") +" " + ((HisProjectInfo)dataBaseInfo).getVersionName()+" ";
           throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                                     new Object[]
                                     {name});

       }

   }
}