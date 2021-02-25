package com.ihome.smarthomeupdate.module.base.communicate;

import com.ihome.smarthomeupdate.module.base.eventbusmodel.BaseMessageEvent;

public interface ICommunicate {

    interface  Listener{
       void onMessage(BaseMessageEvent event);

    }
    void connect(String name);
    void disConnect(String name);
    boolean isConnected(String name);

}
