package com.mobile.mpasswordkeeper.database;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


/**
 * Created by 836137 on 11-05-2017.
 */

@Entity(
        active = true
)
public class CardDetails {

    @Id(autoincrement = true)
    private Long id;

    @Convert(converter = CardTypeConverter.class, columnType = Integer.class)
    private CardType cardType;

    public enum CardType {
        DEFAULT(0), DEBIT(1), CREDIT(2);

        final int id;

        CardType(int id) {
            this.id = id;
        }
    }

    @Unique
    @NotNull
    private long cardNumber;

    @NotNull
    private Date expiryDate;

    @NotNull
    private int CVV;

    @NotNull
    private String cardName;

    @NotNull
    private int cardPin;

    @NotNull
    private long bankId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 204591900)
    private transient CardDetailsDao myDao;

    @Generated(hash = 354316649)
    public CardDetails(Long id, CardType cardType, long cardNumber, @NotNull Date expiryDate,
            int CVV, @NotNull String cardName, int cardPin, long bankId) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.CVV = CVV;
        this.cardName = cardName;
        this.cardPin = cardPin;
        this.bankId = bankId;
    }

    @Generated(hash = 1284525059)
    public CardDetails() {
    }

    public Long getId() {
        return this.id;
    }

 /*   public void setId(Long id) {
        this.id = id;
    }*/

    public CardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public long getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCVV() {
        return this.CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardPin() {
        return this.cardPin;
    }

    public void setCardPin(int cardPin) {
        this.cardPin = cardPin;
    }

    public long getBankId() {
        return this.bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
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

    public void setId(Long id) {
        this.id = id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1726273807)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCardDetailsDao() : null;
    }
}
