package org.example.service;

import org.example.dao.VatRulesDao;
import org.example.dao.VatRulesDaoImpl;
import org.example.model.Country;
import org.example.model.Type;
import org.example.model.VatRules;
import org.example.util.JdbcUtil;
import org.example.util.Loader;
import org.example.util.Settings;

import javax.sql.DataSource;

public class VatRulesServiceImpl implements VatRulesService {
    private DataSource dataSource;
    private VatRulesDao vatRulesDao;

    public VatRulesServiceImpl() {
        this.dataSource = JdbcUtil.createDefaultDataSource();
        this.vatRulesDao = new VatRulesDaoImpl(dataSource);
    }

    @Override
    public VatRules buildVatFromLoader() {
        String country = Loader.kayValueParams.get(Settings.VAT);
        String type = Loader.singleParam.get(2);

        if ((country != null) && (type != null)) return vatRulesDao.findVatByCountryAndType(Country.valueOf(country.toUpperCase()), Type.valueOf(type.toUpperCase()));
        return null;
    }
}
