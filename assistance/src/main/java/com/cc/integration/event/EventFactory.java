/**
 * 
 */
package com.cc.integration.event;

import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventStatusEnum;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.event.impl.ActivityEvent;
import com.cc.integration.event.impl.CheckinEvent;
import com.cc.integration.event.impl.ExchangeEvent;
import com.cc.integration.event.impl.DepositEvent;
import com.cc.integration.event.impl.LoginEvent;
import com.cc.integration.event.impl.OrderEvent;
import com.cc.integration.event.impl.RegisterEvent;

/**
 * @author Administrator
 *
 */
public class EventFactory {

	/**
	 * 获取积分事件
	 * @param integrationEventBean
	 * @return
	 */
	public static Event createEvent(IntegrationEventBean integrationEventBean) {
		IntegrationEventStatusEnum integrationEventStatusEnum = IntegrationEventStatusEnum.getIntegrationEventStatusEnumByCode(integrationEventBean.getStatus());
		if (IntegrationEventStatusEnum.OFF.equals(integrationEventStatusEnum)) {
			return null;
		}
		IntegrationEventTypeEnum integrationEventTypeEnum =  IntegrationEventTypeEnum.getIntegrationEventTypeEnumByCode(integrationEventBean.getEventType());
		if (integrationEventTypeEnum==null) {
			return null;
		}
		Event event = null;
		switch (integrationEventTypeEnum) {
		case REGISTER:
			event = new RegisterEvent();
			break;
		
		case LOGIN:
			event = new LoginEvent();
			break;
			
		case CHECKIN:
			event = new CheckinEvent();
			break;
			
		case EXCHANGE:
			event = new ExchangeEvent();
			break;
			
		case ACTIVITY:
			event = new ActivityEvent();
			break;
			
		case DEPOSIT:
			event = new DepositEvent();
			break;
			
		case BUY:
			event = new OrderEvent();
			break;

		default:
			break;
		}
		if(event!=null){
			event.setIntegrationEventBean(integrationEventBean);
		}
		return event;
	}
}
