package de.alpharogroup.rewrite;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netflix.rewrite.ast.Tr;
import com.netflix.rewrite.parse.OracleJdkParser;
import com.netflix.rewrite.parse.Parser;

public class AddImportTest {
    Parser parser = new OracleJdkParser();

    @Test
    public void refactorMethodName() {
        String a = "import java.util.Collection;"
        		+ "import java.util.Set;"
        		+ "class A {}";

        final Tr.CompilationUnit cu = parser.parse(a);

        Tr.CompilationUnit fixed = cu.refactor().addImport("java.util.List").fix();    
        
        String actual = fixed.print();
        String expected = "import java.util.Collection;\nimport java.util.List;\nimport java.util.Set;class A {}";

        assertEquals(expected, actual);
    }
}
