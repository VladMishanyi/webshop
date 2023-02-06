package org.example.service;

import org.example.model.AccountRules;

public interface AccountRulesService {
    AccountRules buildAccountFromLoader(String name, String email);
}
