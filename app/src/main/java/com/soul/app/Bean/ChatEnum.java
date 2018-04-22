package com.soul.app.Bean;

/**
 * Created by Rahul on 4/2/2016.
 */
public enum ChatEnum {
    /**
     * 1
     **/
    Message,
    /**
     * 2
     **/
    JoinGroup,
    /**
     * 3
     **/
    UserTyping,
    /**
     * 4
     **/
    OnetoOneMessage,
    /**
     * 5
     **/
    SendFile,
    /**
     * 6
     **/
    GroupInvitation,
    /**
     * 7
     **/
    LikeMessage,
    /**
     * 8
     **/
    StarMessage,
    /**
     * 9
     **/
    LeaveGroup,
    /**
     * 10
     **/
    NewTeamMember,
    /**
     * 11
     **/
    NewGroupMember,
    /**
     * 12
     **/
    DeleteMessage,
    /**
     * 13
     **/
    DeleteFile,
    /**
     * 14
     **/
    Comment,
    /**
     * 15
     **/
    FileShare,
    /**
     * 16
     **/
    Onlinestatus,
    /**
     * 17
     **/
    Ping,
    /**
     * 18
     **/
    Useronlinewhileconnect,
    /**
     * 19
     **/
    CreateGroup,
    /**
     * 20
     **/
    Ft_Connect,
    /**
     * 21
     **/
    Video_Call_Review;

    public int getIndex() {
        return ordinal() + 1;
    }
}
