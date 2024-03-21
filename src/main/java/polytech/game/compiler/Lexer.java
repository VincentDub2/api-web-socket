package polytech.game.compiler;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lexer {
    public static class Token {
        public final String command;
        public final String direction;
        public final int steps;

        public Token(String command, String direction, int steps) {
            this.command = command;
            this.direction = direction;
            this.steps = steps;
        }

        @Override
        public String toString() {
            return String.format("Token[command=%s, direction=%s, steps=%d]", command, direction, steps);
        }
    }

    public List<Token> tokenize(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<Token> tokens = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length == 3 && parts[0].equalsIgnoreCase("car")) {
                String direction = parts[1];
                int steps;
                try {
                    steps = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid step count: " + parts[2]);
                }
                tokens.add(new Token(parts[0], direction, steps));
            } else {
                throw new IllegalArgumentException("Invalid command format: " + line);
            }
        }
        return tokens;
    }
}
