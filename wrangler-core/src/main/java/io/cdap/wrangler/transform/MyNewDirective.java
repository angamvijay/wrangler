package io.cdap.wrangler.transform;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.annotations.Description;
import io.cdap.wrangler.api.annotations.Name;
import io.cdap.wrangler.api.annotations.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple custom directive that appends "-custom" to a given column value.
 */
@Plugin(type = Directive.Type.TRANSFORM)
@Name("MyNewDirective")
@Description("Appends '-custom' to the specified column value.")
public class MyNewDirective implements Directive {

    private String column;

    @Override
    public void initialize(DirectiveContext context) throws DirectiveParseException {
        // No initialization needed for this simple example
    }

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("MyNewDirective")
                .withOptionalArg("column")
                .build();
    }

    @Override
    public void initialize(Arguments arguments) throws DirectiveParseException {
        this.column = arguments.value("column");
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
        List<Row> output = new ArrayList<>();
        for (Row row : rows) {
            if (row.has(column)) {
                Object value = row.getValue(column);
                if (value != null) {
                    row.setValue(column, value.toString() + "-custom");
                }
            }
            output.add(row);
        }
        return output;
    }
}
