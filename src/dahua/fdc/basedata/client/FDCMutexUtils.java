package com.kingdee.eas.fdc.basedata.client;

import java.util.Iterator;
import java.util.List;

import com.kingdee.eas.base.netctrl.IMutexServiceControl;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;

/**
 * 房地产网络互斥工具类
 * @author liupd
 *
 */
public class FDCMutexUtils {

	/**
     * 
     * 描述：申请网络互斥
     * @param ui 当前UI
     * @param id 数据ID
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, String id) {
    	setMutex(ui, id);
    }
    
    /**
     * 
     * 描述：申请互斥
     * @param ui
     * @param ids 数据ID数组
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
    		setMutex(ui, ids[i]);	
		}
    	
    }
    
    /**
     * 
     * 描述：申请互斥
     * @param ui
     * @param ids 数据ID List
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, List ids) {
    	for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			setMutex(ui, id);
		}
    }
    
   
    /**
     * 
     * 描述：释放互斥
     * @param id 数据Id
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void releaseMutex(String id) {
    	IMutexServiceControl mutexServiceControl = FrameWorkClientUtils.createMutexServiceControl();
    	mutexServiceControl.releaseObjIDForUpdate(id);
    }
    
    /**
     * 
     * 描述：释放互斥
     * @param ids 数据Id List
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void releaseMutex(List ids) {
    	
    	for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			releaseMutex(id);
		}
    }
    
    /**
     * 
     * 描述：释放互斥
     * @param ids 数据Id数组
     * @author:liupd
     * 创建时间：2006-4-5 <p>
     */
    public static void releaseMutex(String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
			releaseMutex(ids[i]);
		}
    }
    
	/**
	 * 设置数据互斥
	 * @param id
	 */
	public  static void setMutex(CoreUI ui,String id){
		//网络对象互斥,检查是否有人锁定此对象
		try {
//			String oprt=ui.getOprtState();
//			ui.setOprtState( "REMOVE" ) ;
			ui.pubFireVOChangeListener(id);
//			ui.setOprtState(oprt);
		} catch (Throwable ex) {
			ui.handUIException(ex);
			SysUtil.abort();
		}
	}
}
