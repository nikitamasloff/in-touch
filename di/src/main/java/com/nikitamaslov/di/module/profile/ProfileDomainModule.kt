package com.nikitamaslov.di.module.profile

import com.nikitamaslov.profiledomain.interactor.*
import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import dagger.Module
import dagger.Provides

@Module
class ProfileDomainModule {

    @Provides
    fun provideChangeCredentialKeyUseCase(
        profileAuthRepository: ProfileAuthRepository
    ): ChangeCredentialKeyUseCase {
        return ChangeCredentialKeyUseCaseImpl(profileAuthRepository)
    }

    @Provides
    fun provideChangeCredentialTokenUseCase(
        profileAuthRepository: ProfileAuthRepository
    ): ChangeCredentialTokenUseCase {
        return ChangeCredentialTokenUseCaseImpl(profileAuthRepository)
    }

    @Provides
    fun provideChangeDescriptionUseCase(
        profileRepository: ProfileRepository
    ): ChangeDescriptionUseCase {
        return ChangeDescriptionUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideChangeInitialsUseCase(
        profileRepository: ProfileRepository
    ): ChangeInitialsUseCase {
        return ChangeInitialsUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideFollowProfileByIdUseCase(
        profileRepository: ProfileRepository
    ): FollowProfileByIdUseCase {
        return FollowProfileByIdUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideGetFollowersByIdUseCase(
        profileRepository: ProfileRepository
    ): GetFollowersByIdUseCase {
        return GetFollowersByIdUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideGetSubscriptionsByIdUseCase(
        profileRepository: ProfileRepository
    ): GetSubscriptionsByIdUseCase {
        return GetSubscriptionsByIdUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideObserveMyProfileUseCase(
        profileRepository: ProfileRepository
    ): ObserveMyProfileUseCase {
        return ObserveMyProfileUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideObserveProfileByIdUseCase(
        profileRepository: ProfileRepository
    ): ObserveProfileByIdUseCase {
        return ObserveProfileByIdUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideSearchProfilesUseCase(
        profileRepository: ProfileRepository
    ): SearchProfilesUseCase {
        return SearchProfilesUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideUnfollowProfileByIdUseCase(
        profileRepository: ProfileRepository
    ): UnfollowProfileByIdUseCase {
        return UnfollowProfileByIdUseCaseImpl(profileRepository)
    }

    @Provides
    fun provideLogOutUseCase(
        profileAuthRepository: ProfileAuthRepository
    ): LogOutUseCase {
        return LogOutUseCaseImpl(profileAuthRepository)
    }
}