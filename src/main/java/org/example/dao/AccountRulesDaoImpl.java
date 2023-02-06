package org.example.dao;

import org.example.dao.exception.DaoOperationException;
import org.example.model.AccountRules;
import org.example.model.Country;
import org.example.model.Currency;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRulesDaoImpl implements AccountRulesDao {
    private final static String INSERT_ACCOUNT_RULES_SQL = "INSERT INTO account_rules(db_name, db_email, db_input_currency, db_output_currency, db_country) VALUES (?, ?, ?, ?, ?);";
    private final static String SELECT_ACCOUNT_RULES_BY_ID_SQL = "SELECT * FROM account_rules WHERE account_rules.db_id = ?;";
    private final static String SELECT_ALL_ACCOUNTS_RULES_SQL = "SELECT * FROM account_rules;";
    private final static String UPDATE_ACCOUNT_RULES_SQL = "UPDATE account_rules SET db_name =?, db_email = ?, db_input_currency = ?, db_output_currency = ?, db_country = ? WHERE db_id = ?;";
    private DataSource dataSource;

    public AccountRulesDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(AccountRules account) {
        try (Connection connection = dataSource.getConnection()) {
            saveAccountRules(account, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage(), e);
        }
    }

    private void saveAccountRules(AccountRules account, Connection connection) throws SQLException {
        PreparedStatement insertStatement = prepareInsertStatement(connection, account);
        executeUpdate(insertStatement, "Account was not created");
        Long id = fetchGeneratedId(insertStatement);
        account.setId(id);
    }

    private PreparedStatement prepareInsertStatement(Connection connection, AccountRules account) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_ACCOUNT_RULES_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            return fillStatementWithAccountData(insertStatement, account);
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot prepare statement to insert account", e);
        }
    }

    private PreparedStatement fillStatementWithAccountData(PreparedStatement insertStatement, AccountRules account)
            throws SQLException {
        insertStatement.setString(1, account.getName());
        insertStatement.setString(2, account.getEmail());
        insertStatement.setString(3, account.getInputCurrency() == null ? null : account.getInputCurrency().name());
        insertStatement.setString(4, account.getOutputCurrency() == null ? null : account.getOutputCurrency().name());
        insertStatement.setString(5, account.getCountry() == null ? null : account.getCountry().name());
        return insertStatement;
    }

    private void executeUpdate(PreparedStatement insertStatement, String errorMessage) throws SQLException {
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected == 0) {
            throw new DaoOperationException(errorMessage);
        }
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new DaoOperationException("Can not obtain an account ID");
        }
    }

    @Override
    public AccountRules findOne(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return findAccountById(id, connection);
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot find Account by id = %d", id), e);
        }
    }

    private AccountRules findAccountById(Long id, Connection connection) throws SQLException {
        PreparedStatement selectByIdStatement = prepareSelectByIdStatement(id, connection);
        ResultSet resultSet = selectByIdStatement.executeQuery();
        resultSet.next();
        return parseRow(resultSet);
    }

    private PreparedStatement prepareSelectByIdStatement(Long id, Connection connection) {
        try {
            PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_ACCOUNT_RULES_BY_ID_SQL);
            selectByIdStatement.setLong(1, id);
            return selectByIdStatement;
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot prepare statement to select account by id", e);
        }
    }

    private AccountRules parseRow(ResultSet rs) throws SQLException {
        AccountRules account = new AccountRules();
        account.setId(rs.getLong(1));
        account.setCreateOn(rs.getTimestamp(2).toLocalDateTime());
        account.setUpdateOn(rs.getTimestamp(3).toLocalDateTime());
        account.setName(rs.getString(4));
        account.setEmail(rs.getString(5));
        account.setInputCurrency(rs.getString(6) == null ? null : Currency.valueOf(rs.getString(6)));
        account.setOutputCurrency(rs.getString(7) == null ? null : Currency.valueOf(rs.getString(7)));
        account.setCountry(rs.getString(8) == null ? null : Country.valueOf(rs.getString(8)));
        return account;
    }

    @Override
    public List<AccountRules> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_ACCOUNTS_RULES_SQL);
            return collectToList(rs);
        } catch (SQLException e) {
            throw new DaoOperationException(e.getMessage());
        }
    }

    private List<AccountRules> collectToList(ResultSet rs) throws SQLException {
        List<AccountRules> accountList = new ArrayList<>();
        while (rs.next()) {
            AccountRules account = parseRow(rs);
            accountList.add(account);
        }

        return accountList;
    }

    @Override
    public void update(AccountRules account) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement updateStatement = prepareUpdateStatement(account, connection);
            executeUpdate(updateStatement, "Account was not updated");
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot update Account with id = %d", account.getId()), e);
        }
    }

    private PreparedStatement prepareUpdateStatement(AccountRules account, Connection connection) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_ACCOUNT_RULES_SQL);
            fillStatementWithAccountData(updateStatement, account);
            updateStatement.setLong(6, account.getId());
            return updateStatement;
        } catch (SQLException e) {
            throw new DaoOperationException(String.format("Cannot prepare update statement for account id = %d", account.getId()), e);
        }
    }
}
