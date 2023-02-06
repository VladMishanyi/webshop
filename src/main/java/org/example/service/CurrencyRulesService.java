package org.example.service;

import org.example.model.CurrencyRules;

public interface CurrencyRulesService {
    CurrencyRules buildInputCurrencyFromLoader();
    CurrencyRules buildOutputCurrencyFromLoader();
}
