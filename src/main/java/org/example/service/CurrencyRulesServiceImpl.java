package org.example.service;

import org.example.dao.CurrencyRulesDao;
import org.example.dao.CurrencyRulesDaoImpl;
import org.example.model.Currency;
import org.example.model.CurrencyRules;
import org.example.util.JdbcUtil;
import org.example.util.Loader;
import org.example.util.Settings;

import javax.sql.DataSource;

public class CurrencyRulesServiceImpl implements CurrencyRulesService {
    private DataSource dataSource;
    private CurrencyRulesDao currencyRulesDao;

    public CurrencyRulesServiceImpl() {
        this.dataSource = JdbcUtil.createDefaultDataSource();
        this.currencyRulesDao = new CurrencyRulesDaoImpl(dataSource);
    }

    @Override
    public CurrencyRules buildInputCurrencyFromLoader() {
        String inputCurrency = Loader.kayValueParams.get(Settings.INPUT_CURRENCY);

        if (inputCurrency != null) return currencyRulesDao.findByName(Currency.valueOf(inputCurrency.toUpperCase()));
        return null;
    }

    @Override
    public CurrencyRules buildOutputCurrencyFromLoader() {
        String outputCurrency = Loader.kayValueParams.get(Settings.OUTPUT_CURRENCY);

        if (outputCurrency != null) return currencyRulesDao.findByName(Currency.valueOf(outputCurrency.toUpperCase()));
        return null;
    }
}
