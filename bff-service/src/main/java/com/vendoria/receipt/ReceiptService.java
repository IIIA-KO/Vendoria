package com.vendoria.receipt;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReceiptService {
    private final ResourceBundle messages;
    private final Locale locale;

    public ReceiptService(Locale locale) {
        this.locale = locale;
        this.messages = ResourceBundle.getBundle("receipt", locale);
    }

    public String generateReceipt(String item, int quantity, double price) {
        double total = quantity * price;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        StringBuilder receipt = new StringBuilder();
        receipt.append(messages.getString("receipt.title")).append("\n")
                .append(messages.getString("item.label")).append(": ").append(item).append("\n")
                .append(messages.getString("quantity.label")).append(": ").append(quantity).append("\n")
                .append(messages.getString("price.label")).append(": ").append(currencyFormat.format(price)).append("\n")
                .append(messages.getString("total.label")).append(": ").append(currencyFormat.format(total)).append("\n");

        return receipt.toString();
    }
}
