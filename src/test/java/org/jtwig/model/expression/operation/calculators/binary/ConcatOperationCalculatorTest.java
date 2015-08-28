package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.binary.calculators.ConcatOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConcatOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);
    private ConcatOperationCalculator underTest = new ConcatOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(context.environment().valueConfiguration()).thenReturn(new DefaultValueConfiguration());
    }

    @Test
    public void calculate() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value("Hello ", new DefaultValueConfiguration()));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value("World", new DefaultValueConfiguration()));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asString(), is("Hello World"));
    }
}