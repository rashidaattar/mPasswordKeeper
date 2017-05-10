package com.mobile.mpasswordkeeper.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by 836137 on 10-05-2017.
 */

@Entity(
        active = true
)
public class BankDetails {

    @Id(autoincrement = true)
    private long id;

    @NotNull
    private String bankName;

    @NotNull
    private String bankBranch;

    @NotNull
    @Unique
    private String accountNo;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1488789962)
private transient BankDetailsDao myDao;

@Generated(hash = 74437050)
public BankDetails(long id, @NotNull String bankName,
        @NotNull String bankBranch, @NotNull String accountNo) {
    this.id = id;
    this.bankName = bankName;
    this.bankBranch = bankBranch;
    this.accountNo = accountNo;
}

@Generated(hash = 1406348723)
public BankDetails() {
}

public long getId() {
    return this.id;
}

public void setId(long id) {
    this.id = id;
}

public String getBankName() {
    return this.bankName;
}

public void setBankName(String bankName) {
    this.bankName = bankName;
}

public String getBankBranch() {
    return this.bankBranch;
}

public void setBankBranch(String bankBranch) {
    this.bankBranch = bankBranch;
}

public String getAccountNo() {
    return this.accountNo;
}

public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
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
@Generated(hash = 80936693)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getBankDetailsDao() : null;
}
}
