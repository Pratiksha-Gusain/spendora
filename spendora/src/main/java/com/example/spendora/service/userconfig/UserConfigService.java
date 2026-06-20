package com.example.spendora.service.userconfig;

import com.example.spendora.dto.UserConfigDto;
import com.example.spendora.model.UserConfig;

public interface UserConfigService {
    UserConfig getByUserId(String appUserId);
    UserConfigDto getConfig(String userId);
    UserConfigDto updateConfig(String userId, UserConfigDto dto);
}

