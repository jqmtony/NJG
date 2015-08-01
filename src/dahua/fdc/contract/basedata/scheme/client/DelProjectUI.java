/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.CryptoUtil;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.UserException;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FdcF7InitHelper;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class DelProjectUI extends AbstractDelProjectUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4882735658243821371L;

	private static final Logger logger = CoreUIObject.getLogger(DelProjectUI.class);
    
    /**
     * output class constructor
     */
    public DelProjectUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
        // 调用基类方法装载数据
        super.onLoad();  
        
		// 检查是否Administrator登录
		checkIsLogin();
        
    	FdcF7InitHelper.initCostCenterF7(kDBizPromptBox1, this, FDCConstants.CORP_CU, FDCConstants.CORP_CU, true);
    	
        actionDelProject.setEnabled(true);
        
        String txt = " 请谨慎使用该功能，确定做好数据备份。" +
        		" 本功能将删除与 选择的项目组织相关的所有 项目综合管理下的业务数据";
        kDTextArea1.setText(txt);
    }

    /**
     * output actionDelProject_actionPerformed
     */
    public void actionDelProject_actionPerformed(ActionEvent e) throws Exception
    {
    	OrgUnitInfo orgUnit = (OrgUnitInfo)kDBizPromptBox1.getValue();
        if(orgUnit==null){
        	MsgBox.showWarning(this, "请选择项目组织");
        	return ;
        }   
        
    	Map params = new HashMap();
    	
    	params.put("DelProject", Boolean.TRUE);
    	params.put("orgUnit", orgUnit);
    	UIUtils.getRemoteInterFace().updateData(params);
    }
    
	// /////////////////////////////////////////////////////////////////////////
	// 管理员密码校验
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * 检查是否Administrator登录
	 * 
	 * @throws Exception
	 */
	public void checkIsLogin() throws Exception {
		boolean flag = isLogin();
		if (!flag) {
			abort();
		}
	}

	/**
	 * 是否登录
	 * 
	 * @return
	 * @throws EASBizException
	 */
	public boolean isLogin() throws EASBizException {
		UserInfo userInfo = ContextHelperFactory.getRemoteInstance().getCurrentUser();
		if (com.kingdee.eas.base.permission.Administrator.isSuperCUAdmin(new ObjectUuidPK(userInfo.getId()))) {
			return true;
		}

		if (Administrator.ID.equals(userInfo.getId().toString())) {
			return true;
		}

		try {
			userInfo = UserFactory.getRemoteInstance().getUserByID(new ObjectUuidPK(Administrator.ID));
		} catch (BOSException e) {
			logger.error("", e);
		}

		JPasswordField pwd = new JPasswordField();

		Object[] message = {
				com.kingdee.eas.util.client.EASResource.getString(
						"com.kingdee.eas.fm.common.COMMONAutoGenerateResource", "49_FMIsqlUI"), pwd };

		int res = JOptionPane.showConfirmDialog(this, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "请输入管理员密码!", "");
		String logonPassword = new String(pwd.getPassword());

		if (matchPassword(userInfo, logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * 匹配密码
	 * 
	 * @param userInfo
	 * @param password
	 * @return
	 * @throws EASBizException
	 */
	public static boolean matchPassword(UserInfo userInfo, String password) throws EASBizException {
		if (StringUtils.isEmpty(password)) {
			return StringUtils.isEmpty(userInfo.getPassword());
		} else {
			return encrypt(userInfo.getId().toString(), password).equals(userInfo.getPassword());
		}
	}

	/**
	 * 加密
	 * 
	 * @param userID
	 * @param password
	 * @return
	 * @throws EASBizException
	 */
	public static String encrypt(String userID, String password) throws EASBizException {

		try {
			return CryptoUtil.encrypt(userID + password.trim());
		} catch (Exception e) {
			throw new UserException(UserException.ENCRYPT_FAIL);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////

}