package polytech.game.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class ParserTest {

    @Test
    public void testParseCommands() {
        Parser parser = new Parser();
        List<Lexer.Token> tokens = Arrays.asList(
                new Lexer.Token("car", "right", 5),
                new Lexer.Token("car", "left", 3)
        );
        Parser.Program program = parser.parse(tokens);

        assertEquals(2, program.commands.size());
        assertEquals("right", program.commands.get(0).action);
        assertEquals(5, program.commands.get(0).value);
        assertEquals("left", program.commands.get(1).action);
        assertEquals(3, program.commands.get(1).value);
    }
}
