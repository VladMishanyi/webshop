package org.example.dao;

import org.example.dao.exception.DaoOperationException;
import org.example.model.Currency;
import org.example.model.CurrencyRules;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRulesDaoImpl implements CurrencyRulesDao {
    private final static String SELECT_CURRENCY_RULES_BY_NAME_SQL = "SELECT * FROM currency_rules WHERE currency_rules.db_currency = ?;";
    private DataSource dataSource;

    public CurrencyRulesDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CurrencyRules findByName(Currency currency) {
        try (Connection connection = dataSource.getConnection()) {
            return findCurrencyByName(currency.name(), connection);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot find Currency by name = %s", currency.name()), e);
        }
    }

    private CurrencyRules findCurrencyByName(String name, Connection connection) throws SQLException {
        PreparedStatement selectByIdStatement = prepareSelectByIdStatement(name, connection);
        ResultSet resultSet = selectByIdStatement.executeQuery();
        resultSet.next();
        return parseRow(resultSet);
    }

    private PreparedStatement prepareSelectByIdStatement(String name, Connection connection) {
        try {
            PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_CURRENCY_RULES_BY_NAME_SQL);
            selectByIdStatement.setString(1, name);
            return selectByIdStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot prepare statement to select account by id", e);
        }
    }

    private CurrencyRules parseRow(ResultSet rs) throws SQLException {
        CurrencyRules currency = new CurrencyRules();
        currency.setId(rs.getLong(1));
        currency.setCreateOn(rs.getTimestamp(2).toLocalDateTime());
        currency.setUpdateOn(rs.getTimestamp(3).toLocalDateTime());
        currency.setCurrency(rs.getString(4) == null ? null : Currency.valueOf(rs.getString(4)));
        currency.setRate(rs.getDouble(5));
        return currency;
    }
}
