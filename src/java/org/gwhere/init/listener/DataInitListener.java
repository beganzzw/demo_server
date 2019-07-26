package org.gwhere.init.listener;

import org.gwhere.init.service.DataInitService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private DataInitService dataInitServivce;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataInitServivce.initPermissionData();
    }

}
