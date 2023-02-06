package org.example.dao;

import org.example.model.Currency;
import org.example.model.CurrencyRules;

public interface CurrencyRulesDao {

    CurrencyRules findByName(Currency currency);
}
