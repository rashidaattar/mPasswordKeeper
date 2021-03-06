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
public class OtherDetails {

    @Id(autoincrement = true)
    private Long id;

    private String title;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    private String additionalInfo;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1774167868)
private transient OtherDetailsDao myDao;

@Generated(hash = 2036453037)
public OtherDetails(Long id, String title, @NotNull String userName,
        @NotNull String password, String additionalInfo) {
    this.id = id;
    this.title = title;
    this.userName = userName;
    this.password = password;
    this.additionalInfo = additionalInfo;
}

@Generated(hash = 744118041)
public OtherDetails() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getUserName() {
    return this.userName;
}

public void setUserName(String userName) {
    this.userName = userName;
}

public String getPassword() {
    return this.password;
}

public void setPassword(String password) {
    this.password = password;
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

public String getAdditionalInfo() {
    return this.additionalInfo;
}

public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1454954272)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getOtherDetailsDao() : null;
}
}
