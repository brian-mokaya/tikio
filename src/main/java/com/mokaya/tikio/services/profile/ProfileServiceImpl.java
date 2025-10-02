package com.mokaya.tikio.services.profile;

import com.mokaya.tikio.models.Profile;
import com.mokaya.tikio.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public Profile updateProfile(Long userId, Profile profileDetails) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());
        profile.setCity(profileDetails.getCity());
        profile.setCountry(profileDetails.getCountry());

        return profile; // persistence context auto-updates due to @Transactional
    }

    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }
}
