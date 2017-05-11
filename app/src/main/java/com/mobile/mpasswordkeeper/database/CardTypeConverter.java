package com.mobile.mpasswordkeeper.database;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by 836137 on 11-05-2017.
 */

public class CardTypeConverter implements PropertyConverter<CardDetails.CardType, Integer> {
    @Override
    public CardDetails.CardType convertToEntityProperty(Integer databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        for (CardDetails.CardType cardType : CardDetails.CardType.values()) {
            if (cardType.id == databaseValue) {
                return cardType;
            }
        }
        return CardDetails.CardType.DEFAULT;
    }

    @Override
    public Integer convertToDatabaseValue(CardDetails.CardType entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
