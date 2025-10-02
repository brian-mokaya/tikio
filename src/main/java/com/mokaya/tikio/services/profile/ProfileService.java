package com.mokaya.tikio.services.profile;

import com.mokaya.tikio.models.Profile;

public interface ProfileService {
    Profile updateProfile(Long userId, Profile profile);
    Profile getProfileByUserId(Long userId);
}
