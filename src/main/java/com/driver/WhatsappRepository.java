package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private static HashMap<Group, List<User>> groupUserMap;
    private static HashMap<Group, List<Message>> groupMessageMap;
    private static HashMap<Message, User> senderMap;
    private static HashMap<Group, User> adminMap;
    private static HashSet<String> userMobile;
    private static int customGroupCount;
    private static int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public static boolean getUserByMobile(String mobile) {
        if(userMobile.contains(mobile)){
            return true;
        }
        return false;
    }

    public static void createGroup(List<User> users, Group gp1) {
        groupUserMap.put(gp1,users);
        adminMap.put(gp1,users.get(0));
    }

    public static Group createBigGroup(List<User> users) {
        customGroupCount++;
        Group gp2=new Group("Group "+customGroupCount,users.size());
        groupUserMap.put(gp2,users);
        adminMap.put(gp2,users.get(0));
        return gp2;
    }

    public static int createMessage(String content) {
        messageId++;
        Date Date = new Date();
        Message m1=new Message(messageId,content,Date);
        return messageId;
    }

    public static boolean getGroupExist(Group group) {
        if(groupUserMap.containsKey(group)){
            return true;
        }
        return false;
    }

    public static boolean checkUserExistInGroup(User sender, Group group) {
        List<User> list1=groupUserMap.get(group);
        for(User x: list1){
            if(x==sender){
                return true;
            }
        }
        return false;
    }

    public static int sendMessage(Message message, User sender, Group group) {
        List<Message> messageList=groupMessageMap.get(group);
        message.setId(messageList.size());
        messageList.add(message);
        groupMessageMap.put(group,messageList);
        senderMap.put(message,sender);
        return messageList.size();
    }

    public static boolean checkGroupAdmin(User approver,Group group) {
        if(adminMap.get(group)==approver){
            return true;
        }
        return false;
    }

    public static void changeAdmin(User user, Group group) {
        adminMap.put(group,user);
    }

    public static Optional<Group> checkUserInGroups(User user) {
        List<Group> userList= (List<Group>) groupUserMap.keySet();
        for(Group x: userList){

            if(groupUserMap.get(x).contains(user)){
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    public static boolean checkUserInAdmin(User user) {
        if(adminMap.containsValue(user)){
            return true;
        }
        return false;
    }

    public static int removeUser(User user, Group group) {
        groupUserMap.get(group).remove(user);

        for(Message x: senderMap.keySet()){
            if(senderMap.get(x)==user){
                senderMap.remove(x);
                groupMessageMap.get(group).remove(x);
            }
        }
        return (groupUserMap.get(group).size()+groupMessageMap.get(group).size()+messageId);
    }

}
