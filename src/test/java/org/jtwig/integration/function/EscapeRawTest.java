package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EscapeRawTest extends AbstractIntegrationTest {
    @Test
    public void latestApply() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | escape | raw }}").render(JtwigModel.newModel());

        assertThat(result, is("&"));
    }

    @Test
    public void latestApplyEscape() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | raw | escape }}").render(JtwigModel.newModel());

        assertThat(result, is("&amp;"));
    }

    @Test
    public void latestApplyEscapeJavascript() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '\"' | raw | escape('js') }}").render(JtwigModel.newModel());

        assertThat(result, is("\\\""));
    }

    @Test
    public void latestApplyEscapeJavascriptVerbose() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '\"' | raw | escape('javascript') }}").render(JtwigModel.newModel());

        assertThat(result, is("\\\""));
    }
}