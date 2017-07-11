package com.mobile.mpasswordkeeper.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by 836137 on 11-07-2017.
 */

@Entity(
        active = true
)
public class EmailDetails {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String emailId;

    @NotNull
    private String password;


    private String alternateEmail;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1757551636)
private transient EmailDetailsDao myDao;


@Generated(hash = 158604127)
public EmailDetails(Long id, @NotNull String emailId, @NotNull String password,
        String alternateEmail) {
    this.id = id;
    this.emailId = emailId;
    this.password = password;
    this.alternateEmail = alternateEmail;
}


@Generated(hash = 166415070)
public EmailDetails() {
}


public Long getId() {
    return this.id;
}


public void setId(Long id) {
    this.id = id;
}


public String getEmailId() {
    return this.emailId;
}


public void setEmailId(String emailId) {
    this.emailId = emailId;
}


public String getPassword() {
    return this.password;
}


public void setPassword(String password) {
    this.password = password;
}


public String getAlternateEmail() {
    return this.alternateEmail;
}


public void setAlternateEmail(String alternateEmail) {
    this.alternateEmail = alternateEmail;
}


/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}


/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}


/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}


/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1030549164)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getEmailDetailsDao() : null;
}




}
