package com.mobile.mpasswordkeeper.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;

/**
 * Created by 836137 on 10-05-2017.
 */

@Entity(
        active = true
)
public class BankDetails {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String bankName;

    @NotNull
    private String bankBranch;

    @NotNull
    @Unique
    private String accountNo;

    private String ifsc;

    private String onlineUsername;

    private String onlinePassword;

    @ToMany(referencedJoinProperty = "bankId")
    private List<CardDetails> cards;

    

    private String title;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1488789962)
private transient BankDetailsDao myDao;

@Generated(hash = 807122727)
public BankDetails(Long id, @NotNull String bankName, @NotNull String bankBranch,
        @NotNull String accountNo, String ifsc, String onlineUsername,
        String onlinePassword, String title) {
    this.id = id;
    this.bankName = bankName;
    this.bankBranch = bankBranch;
    this.accountNo = accountNo;
    this.ifsc = ifsc;
    this.onlineUsername = onlineUsername;
    this.onlinePassword = onlinePassword;
    this.title = title;
}

@Generated(hash = 1406348723)
public BankDetails() {
}

public Long getId() {
    return this.id;
}

/*public void setId(Long id) {
    this.id = id;
}*/

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

public String getIfsc() {
    return this.ifsc;
}

public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
}

public String getOnlineUsername() {
    return this.onlineUsername;
}

public void setOnlineUsername(String onlineUsername) {
    this.onlineUsername = onlineUsername;
}

public String getOnlinePassword() {
    return this.onlinePassword;
}

public void setOnlinePassword(String onlinePassword) {
    this.onlinePassword = onlinePassword;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 536889075)
public List<CardDetails> getCards() {
    if (cards == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        CardDetailsDao targetDao = daoSession.getCardDetailsDao();
        List<CardDetails> cardsNew = targetDao._queryBankDetails_Cards(id);
        synchronized (this) {
            if (cards == null) {
                cards = cardsNew;
            }
        }
    }
    return cards;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 189953180)
public synchronized void resetCards() {
    cards = null;
}

/*public String getIFSC() {
    return this.IFSC;
}

public void setIFSC(String IFSC) {
    this.IFSC = IFSC;
}*/

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public void setId(Long id) {
    this.id = id;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 80936693)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getBankDetailsDao() : null;
}
}
