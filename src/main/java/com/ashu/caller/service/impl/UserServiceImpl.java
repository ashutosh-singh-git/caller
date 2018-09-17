package com.ashu.caller.service.impl;

import com.ashu.caller.dao.entity.ContactsEntity;
import com.ashu.caller.dao.entity.SpamEntity;
import com.ashu.caller.dao.entity.UserEntity;
import com.ashu.caller.dao.repository.ContactsRepository;
import com.ashu.caller.dao.repository.SpamRepository;
import com.ashu.caller.dao.repository.UserRepository;
import com.ashu.caller.model.Contact;
import com.ashu.caller.model.User;
import com.ashu.caller.service.CurrentUserService;
import com.ashu.caller.service.UserService;
import com.ashu.caller.util.DtoUtil;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContactsRepository contactsRepository;
    private final CurrentUserService currentUserService;
    private final SpamRepository spamRepository;

    public UserServiceImpl(UserRepository userRepository, ContactsRepository contactsRepository,
                           CurrentUserService currentUserService, SpamRepository spamRepository) {
        this.userRepository = userRepository;
        this.contactsRepository = contactsRepository;
        this.currentUserService = currentUserService;
        this.spamRepository = spamRepository;
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = userRepository.findByMobile(user.getMobile());
        if (userEntity != null) {
            userEntity.setUpdatedAt(new Date());
            if (!StringUtils.isEmpty(user.getEmail())) {
                userEntity.setEmail(user.getEmail());
            }
            if (!StringUtils.isEmpty(user.getProfilePic())) {
                userEntity.setEmail(user.getProfilePic());
            }

            userEntity = userRepository.saveAndFlush(userEntity);
        }

        return DtoUtil.convertByCopy(userEntity, User.class);
    }

    @Override
    public void markNumberSpam(String mobile) {
        SpamEntity spamEntity = spamRepository.findByMobile(mobile);

        if(spamEntity != null){
            spamEntity.setIsSpam(true);
        }else {
            spamEntity = new SpamEntity();
            spamEntity.setMobile(mobile);
            spamEntity.setIsSpam(true);
        }

        spamRepository.save(spamEntity);
    }

    @Override
    public void uploadContact(List<Contact> contactsList) {
        Long userId = currentUserService.getUserInfo().getUser().getUserId();
        List<ContactsEntity> contactsEntities = contactsList.stream()
                .map(contact -> getContactEntityFromDto(contact, userId))
                .collect(Collectors.toList());
        contactsRepository.saveAll(contactsEntities);
    }

    private ContactsEntity getContactEntityFromDto(Contact contact, Long userId) {
        ContactsEntity contactsEntity = DtoUtil.convertByCopy(contact, ContactsEntity.class);
        contactsEntity.setUserId(userId);
        return contactsEntity;
    }

    @Override
    public void updateContact(Contact contact) {
        Long userId = currentUserService.getUserInfo().getUser().getUserId();
        ContactsEntity contactsEntity = contactsRepository.findByUserIdAndMobile(userId, contact.getMobile());
        if (!StringUtils.isEmpty(contact.getName())) {
            contactsEntity.setName(contact.getName());
        }
        contactsRepository.save(contactsEntity);
    }

    public void populateDatabaseWithDummy() {
        NameGenerator generator = new NameGenerator();
        List<Name> names = generator.generateNames(999);
        for (int i = 0; i < 999; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setMobile(9989745 + String.format("%03d", i));
            userEntity.setName(names.get(i).toString());
            userEntity.setEmail(names.get(i).getFirstName() + "@gmail.com");
            userEntity.setProfilePic("something/special/igf.jpg");
            userEntity.setUpdatedAt(new Date());
            userEntity.setCreatedAt(new Date());

            userEntity = userRepository.save(userEntity);
            List<Name> contactNames = generator.generateNames(10);
            for (int j = 0; j < 10; j++) {
                ContactsEntity contactsEntity = new ContactsEntity();
                contactsEntity.setUserId(userEntity.getUserId());
                contactsEntity.setName(contactNames.get(j).toString());
                contactsEntity.setMobile(8739 + String.format("%04d", i) + String.format("%02d", j));

                contactsRepository.save(contactsEntity);
            }
        }
    }

}
