package org.example.dao;

import org.example.dao.exception.DaoOperationException;
import org.example.model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VatRulesDaoImpl implements VatRulesDao {
    private final static String SELECT_VAT_RULES_BY_COUNTRY_TYPE_SQL = "SELECT v.db_id, v.db_create_on, v.db_update_on, v.db_area, v.db_rate, c.db_country, t.db_type FROM vat_rules as v\n" +
            "    INNER JOIN country_rules as c ON v.db_country_id = c.db_id\n" +
            "    INNER JOIN type_rules as t ON v.db_type_id = t.db_id\n" +
            "         WHERE c.db_country = ? AND t.db_type = ?;";
    private DataSource dataSource;

    public VatRulesDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public VatRules findVatByCountryAndType(Country country, Type type) {
        try (Connection connection = dataSource.getConnection()) {
            return findByCountryAndType(country.name(), type.name(), connection);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot find Currency by name = %s", country.name()), e);
        }
    }

    private VatRules findByCountryAndType(String country, String type, Connection connection) throws SQLException {
        PreparedStatement selectByIdStatement = prepareSelectByIdStatement(country, type, connection);
        ResultSet resultSet = selectByIdStatement.executeQuery();
        resultSet.next();
        return parseRow(resultSet);
    }

    private PreparedStatement prepareSelectByIdStatement(String country, String type, Connection connection) {
        try {
            PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_VAT_RULES_BY_COUNTRY_TYPE_SQL);
            selectByIdStatement.setString(1, country);
            selectByIdStatement.setString(2, type);
            return selectByIdStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot prepare statement to select account by id", e);
        }
    }

    private VatRules parseRow(ResultSet rs) throws SQLException {
        VatRules vat = new VatRules();
        vat.setId(rs.getLong(1));
        vat.setCreateOn(rs.getTimestamp(2).toLocalDateTime());
        vat.setUpdateOn(rs.getTimestamp(3).toLocalDateTime());
        vat.setArea(rs.getString(4));
        vat.setRate(rs.getDouble(5));
        vat.setCountry(rs.getString(6) == null ? null : Country.valueOf(rs.getString(6)));
        vat.setType(rs.getString(7) == null ? null : Type.valueOf(rs.getString(7)));
        return vat;
    }
}
