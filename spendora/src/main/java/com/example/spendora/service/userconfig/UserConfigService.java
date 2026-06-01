package com.example.spendora.service.userconfig;

import com.example.spendora.model.UserConfig;

public interface UserConfigService {
    UserConfig getByUserId(String appUserId);
}
