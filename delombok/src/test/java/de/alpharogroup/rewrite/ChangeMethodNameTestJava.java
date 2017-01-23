package de.alpharogroup.rewrite;


import com.netflix.rewrite.ast.Tr;
import com.netflix.rewrite.parse.OracleJdkParser;
import com.netflix.rewrite.parse.Parser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangeMethodNameTestJava {
    Parser parser = new OracleJdkParser();

    @Test
    public void refactorMethodName() {
        String a = "class A {{ B.foo(0); }}";
        String b = "class B { static void foo(int n) {} }";

        final Tr.CompilationUnit cu = parser.parse(a, /* which depends on */ b);

        Tr.CompilationUnit fixed = cu.refactor()
                .changeMethodName(cu.findMethodCalls("B foo(int)"), "bar")
                .fix();

        assertEquals(fixed.print(), "class A {{ B.bar(0); }}");
    }

    @Test
    public void refactorMethodNameDiff() {
        String a = "class A {\n   {\n      B.foo(0);\n   }\n}";
        String b = "class B { static void foo(int n) {} }";

        final Tr.CompilationUnit cu = parser.parse(a, /* which depends on */ b);

        String diff = cu.refactor()
                .changeMethodName(cu.findMethodCalls("B foo(int)"), "bar")
                .diff();

        System.out.println(diff);
    }
}