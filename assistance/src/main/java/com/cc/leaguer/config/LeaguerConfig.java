/**
 * 
 */
package com.cc.leaguer.config;

/**
 * @author Administrator
 *
 */
public class LeaguerConfig {

	/**
	 * 是否实行积分制
	 */
	private boolean integration;
	
	/**
	 * 是否实行会员等级制
	 */
	private boolean gradeIntegration;

	/**
	 * @return the integration
	 */
	public boolean isIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(boolean integration) {
		this.integration = integration;
	}

	/**
	 * @return the gradeIntegration
	 */
	public boolean isGradeIntegration() {
		return gradeIntegration;
	}

	/**
	 * @param gradeIntegration the gradeIntegration to set
	 */
	public void setGradeIntegration(boolean gradeIntegration) {
		this.gradeIntegration = gradeIntegration;
	}
}
