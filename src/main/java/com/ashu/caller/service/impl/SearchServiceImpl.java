package com.ashu.caller.service.impl;

import com.ashu.caller.dao.entity.ContactsEntity;
import com.ashu.caller.dao.entity.SpamEntity;
import com.ashu.caller.dao.repository.ContactsRepository;
import com.ashu.caller.dao.repository.SpamRepository;
import com.ashu.caller.dao.repository.UserRepository;
import com.ashu.caller.model.UserSearch;
import com.ashu.caller.service.CurrentUserService;
import com.ashu.caller.dao.entity.UserEntity;
import com.ashu.caller.exception.CallerException;
import com.ashu.caller.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ContactsRepository contactsRepository;
    private final UserRepository userRepository;
    private final SpamRepository spamRepository;
    private final CurrentUserService currentUserService;

    public SearchServiceImpl(ContactsRepository contactsRepository, UserRepository userRepository,
                             SpamRepository spamRepository, CurrentUserService currentUserService) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
        this.spamRepository = spamRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<UserSearch> searchByMobile(String mobile) {

        UserEntity userEntity = userRepository.findByMobile(mobile);

        if (userEntity != null) {
            UserEntity currentUserEntity = userRepository.findById(currentUserService.getUserInfo().getUser().getUserId()).get();
            List<ContactsEntity> contactsEntities = contactsRepository.findAllByUserId(userEntity.getUserId());
            boolean isCurrentUserAContact = contactsEntities
                    .stream()
                    .anyMatch(va -> va.getMobile().equalsIgnoreCase(currentUserEntity.getMobile()));

            return Collections.singletonList(isCurrentUserAContact ? userSearchWithProfile(userEntity) : userSearchWithoutProfile(userEntity));
        } else {
            List<ContactsEntity> contactsEntitiesWithMobile = contactsRepository.findAllByMobile(mobile);
            if (!contactsEntitiesWithMobile.isEmpty()) {
                return contactsEntitiesWithMobile
                        .parallelStream()
                        .map(this::contactToUser)
                        .collect(Collectors.toList());
            } else {
                throw new CallerException("Number Not Found");
            }
        }
    }

    private UserSearch contactToUser(ContactsEntity contact) {
        UserSearch user = new UserSearch();
        user.setMobile(contact.getMobile());
        user.setName(contact.getName());

        SpamEntity spamEntity = spamRepository.findByMobile(contact.getMobile());
        if (spamEntity != null) {
            user.setIsSpam(spamEntity.getIsSpam());
        }

        return user;
    }

    private UserSearch userSearchWithoutProfile(UserEntity userEntity) {
        UserSearch user = new UserSearch();
        user.setName(userEntity.getName());
        user.setMobile(userEntity.getMobile());

        SpamEntity spamEntity = spamRepository.findByMobile(userEntity.getMobile());
        if (spamEntity != null) {
            user.setIsSpam(spamEntity.getIsSpam());
        }

        return user;
    }

    private UserSearch userSearchWithProfile(UserEntity userEntity) {
        UserSearch user = userSearchWithoutProfile(userEntity);

        user.setEmail(userEntity.getEmail());
        user.setProfilePic(userEntity.getProfilePic());
        return user;
    }

    @Override
    public List<UserSearch> searchByName(String name) {
        List<ContactsEntity> contactsEntities = contactsRepository.searchByName(name);
        if (!contactsEntities.isEmpty()) {
            return contactsEntities.parallelStream()
                    .map(this::contactToUser)
                    .collect(Collectors.toList());
        } else {
            throw new CallerException("No User found with given name");
        }
    }

}
