package org.example.service;

import org.example.dao.AccountRulesDao;
import org.example.dao.AccountRulesDaoImpl;
import org.example.model.AccountRules;
import org.example.model.Country;
import org.example.model.Currency;
import org.example.util.JdbcUtil;
import org.example.util.Loader;
import org.example.util.Settings;

import javax.sql.DataSource;

public class AccountRulesServiceImpl implements AccountRulesService{
    private DataSource dataSource;
    private AccountRulesDao accountRulesDao;

    public AccountRulesServiceImpl() {
        this.dataSource = JdbcUtil.createDefaultDataSource();
        this.accountRulesDao = new AccountRulesDaoImpl(dataSource);
    }

    @Override
    public AccountRules buildAccountFromLoader(String name, String email) {
        String inputCurrency = Loader.kayValueParams.get(Settings.INPUT_CURRENCY);
        String outputCurrency = Loader.kayValueParams.get(Settings.OUTPUT_CURRENCY);
        String country = Loader.kayValueParams.get(Settings.VAT);


        AccountRules accountRules = new AccountRules.Builder()
                .addName(name)
                .addEmail(email)
                .addInputCurrency(inputCurrency == null ? null : Currency.valueOf(inputCurrency.toUpperCase()))
                .addOutputCurrency(outputCurrency == null ? null : Currency.valueOf(outputCurrency.toUpperCase()))
                .addCountry(country == null ? null : Country.valueOf(country.toUpperCase()))
                .build();
        accountRulesDao.save(accountRules);
        return accountRules;
    }
}
