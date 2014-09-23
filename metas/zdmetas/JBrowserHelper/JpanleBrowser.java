package com.kingdee.eas.bpmdemo.JBrowserHelper;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;


public class JpanleBrowser extends JPanel{

	public JpanleBrowser(String openUrl) {
		super(new BorderLayout());
		JPanel webBrowserPanel = new JPanel(new BorderLayout());
		JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.navigate(openUrl);
		
		webBrowser.setButtonBarVisible(false);
		
		webBrowser.setMenuBarVisible(false);
		
		webBrowser.setLocationBarVisible(false);
		
		webBrowser.setBarsVisible(false);
		
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		this.add(webBrowserPanel, BorderLayout.CENTER);
		    
	}
}
