package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WhatsappService {
    public String createUser(String name, String mobile) throws UserAlreadyExistException {
        boolean containsUser=WhatsappRepository.getUserByMobile(mobile);
        if(containsUser){
            throw new UserAlreadyExistException();
        }
        WhatsappRepository.createUser(name,mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        if(users.size()<2){return null;}
        if(users.size()==2){
            Group gp1=new Group(users.get(1).getName(),2);
            WhatsappRepository.createGroup(users,gp1);
            return gp1;
        }
        return WhatsappRepository.createBigGroup(users);
    }

    public int createMessage(String content) {
        return WhatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws GroupDoesNotExistException, NotAllowedToSendMessageException {
        if(!WhatsappRepository.getGroupExist(group)){
            throw new GroupDoesNotExistException();
        }
        if(!WhatsappRepository.checkUserExistInGroup(sender,group)){
            throw new NotAllowedToSendMessageException();
        }
        return WhatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws GroupDoesNotExistException, ApproverDoesNotHaveRight, UserIsNotParticipent {
        if(!WhatsappRepository.getGroupExist(group)){
            throw new GroupDoesNotExistException();
        }
        if(!WhatsappRepository.checkGroupAdmin(approver,group)){
            throw new ApproverDoesNotHaveRight();
        }
        if(!WhatsappRepository.checkUserExistInGroup(user,group)){
            throw new UserIsNotParticipent();
        }
        WhatsappRepository.changeAdmin(user, group);
        return "SUCCESS";
    }

    public int removeUser(User user) throws Exception {
        //checkUserIngroups
        Optional<Group> op3=WhatsappRepository.checkUserInGroups(user);
        if(op3.isEmpty()){
            throw new Exception("User not found");
        }
        boolean checkUserInAdmin=WhatsappRepository.checkUserInAdmin(user);
        if(checkUserInAdmin){
            throw new Exception("Cannot remove admin");
        }
        return WhatsappRepository.removeUser(user, op3.get());
    }

    public String findMessage(Date start, Date end, int k) {
        return "hii";
    }
}
