package org.example.tasks;

import org.example.model.AccountRules;
import org.example.model.Currency;
import org.example.model.CurrencyRules;
import org.example.model.VatRules;
import org.example.service.*;
import org.example.util.Loader;

public class ParserTask {
    private AccountRulesService accountRulesService;
    private CurrencyRulesService currencyRulesService;
    private VatRulesService vatRulesService;

    public ParserTask() {
        this.accountRulesService = new AccountRulesServiceImpl();
        this.currencyRulesService = new CurrencyRulesServiceImpl();
        this.vatRulesService = new VatRulesServiceImpl();
    }

    public void count() {
        String rawAmount = Loader.singleParam.get(0);
        String rawPrice = Loader.singleParam.get(1);
        AccountRules accountRules = accountRulesService.buildAccountFromLoader("user", "user@gmail.com");
        CurrencyRules currencyRulesInput = currencyRulesService.buildInputCurrencyFromLoader();
        CurrencyRules currencyRulesOutput = currencyRulesService.buildOutputCurrencyFromLoader();
        VatRules vatRules = vatRulesService.buildVatFromLoader();


        double amount = rawAmount == null ? 1 : Double.parseDouble(rawAmount);
        double price = rawPrice == null ? 1 : Double.parseDouble(rawPrice);
        double freight = calculateFreightDependsOfAmount(amount);
        double vat = vatRules == null ? 0 : vatRules.getRate();
        double inputCurrency = currencyRulesInput == null ? 1 : convertCurrencyPercentsToValue(currencyRulesInput.getRate());
        double outputCurrency = currencyRulesOutput == null ? 1 : convertCurrencyPercentsToValue(currencyRulesOutput.getRate());

        double result = finalFormula(amount, price, freight, vat, inputCurrency, outputCurrency);

        printResult(result, currencyRulesOutput == null ? Currency.DKK : currencyRulesOutput.getCurrency());
    }

    private double calculateFreightDependsOfAmount(double amount) {
        double standard = 50;
        double border = 10;
        if (amount > border) {
            for (int i=10; amount > i; i+=10) {
                standard += 25;
            }
        }
        return standard;
    }

    private double convertCurrencyPercentsToValue(double percents) {
        return percents / 100;
    }

    private double finalFormula(double amount, double price, double freight, double vat, double inputCurrency, double outputCurrency) {
        return ((amount * price) + freight + vat) * inputCurrency * outputCurrency;
    }

    private void printResult(double value, Currency currency) {
        System.out.println(value + " " + currency.name());
    }
}
