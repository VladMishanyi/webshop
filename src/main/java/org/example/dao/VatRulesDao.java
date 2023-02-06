package org.example.dao;

import org.example.model.Country;
import org.example.model.Type;
import org.example.model.VatRules;

public interface VatRulesDao {
    VatRules findVatByCountryAndType(Country country, Type type);
}
