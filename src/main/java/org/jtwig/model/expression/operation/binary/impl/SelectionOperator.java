package org.jtwig.model.expression.operation.binary.impl;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.model.expression.operation.binary.calculators.SelectionOperationCalculator;

public class SelectionOperator implements BinaryOperator {
    private static final SelectionOperationCalculator CALCULATOR = new SelectionOperationCalculator();

    @Override
    public String symbol() {
        return ".";
    }

    @Override
    public int precedence() {
        return 1;
    }

    @Override
    public BinaryOperationCalculator calculator() {
        return CALCULATOR;
    }
}