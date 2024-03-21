package polytech.game.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LexerTest {

    @Test
    public void testTokenizeSimpleCommands() throws Exception {
        Path tempFile = Files.createTempFile(null, ".txt");
        Files.write(tempFile, List.of("car right 5", "car left 3"));

        Lexer lexer = new Lexer();
        List<Lexer.Token> tokens = lexer.tokenize(tempFile.toString());

        assertEquals(2, tokens.size());
        assertEquals("right", tokens.get(0).direction);
        assertEquals(5, tokens.get(0).steps);
        assertEquals("left", tokens.get(1).direction);
        assertEquals(3, tokens.get(1).steps);

        Files.deleteIfExists(tempFile); // Nettoyer le fichier temporaire
    }
}
