package org.gwhere.init.service.impl;

import org.gwhere.init.service.DataInitService;
import org.gwhere.permission.cache.PermissionCache;
import org.springframework.stereotype.Service;

@Service
public class DataInitServiceImpl implements DataInitService {

    @Override
    public void initPermissionData() {
        if (PermissionCache.isNotInitialized()) {
            PermissionCache.refresh();
        }
    }
}
