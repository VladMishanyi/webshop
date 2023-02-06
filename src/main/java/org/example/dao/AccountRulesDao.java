package org.example.dao;

import org.example.model.AccountRules;

import java.util.List;

public interface AccountRulesDao {
    void save(AccountRules account);

    AccountRules findOne(Long id);

    List<AccountRules> findAll();

    void update(AccountRules account);
}
