package com.norbert.backend.email;

import com.norbert.backend.dto.TransactionDTO;

public class EmailBuilder {
    public static String buildPayrollMessage(BuildingEmailMessageRequest request) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;background-color:#f6f8fa;padding:20px\">\n")
                .append("\n")
                .append("  <div style=\"font-size:28px;font-weight:bold;color:#ffffff;text-align:center;margin-bottom:10px;background-color:#000000;height:50px;line-height:50px\">\n")
                .append("    <span style=\"color:#ffffff;\">Payroll</span>\n")
                .append("  </div>\n")
                .append("\n")
                .append("  <div style=\"font-size:19px;line-height:25px;color:#0b0c0c;margin-bottom:20px\">\n")
                .append("    Hi, ").append(request.employee().getFirstName()).append(" ").append(request.employee().getLastName()).append(".<br><br>\n")
                .append("    Thank you for powerful work. Your salary is ").append(request.salary()).append(" UAN, was paid to the card ").append(request.employee().getCardNumber()).append(" <br><br>\n")
                .append("    Work you have done: <br>\n");

        for (TransactionDTO transaction : request.transactions()) {
            messageBuilder.append("[").append(transaction.getDate()).append("] \"")
                    .append(transaction.getOrderType().getName()).append("\" ")
                    .append(transaction.getOrderType().getPrice()).append(" UAN done by ")
                    .append(transaction.getEmployees()).append("<br>");
        }

        messageBuilder.append("<br>")
                .append("    See you soon!\n")
                .append("  </div>\n")
                .append("\n")
                .append("</div>");

        return messageBuilder.toString();
    }
}
