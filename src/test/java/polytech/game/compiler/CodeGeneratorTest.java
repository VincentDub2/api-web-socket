package polytech.game.compiler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CodeGeneratorTest {

    @Test
    public void testGenerateCode() throws Exception {
        // Création directe d'un programme avec des commandes
        List<Parser.Command> commands = List.of(new Parser.Command("right", 5));
        Parser.Program program = new Parser.Program(commands);

        CodeGenerator codeGenerator = new CodeGenerator();
        String outputPath = "src/test/java/polytech/game/compiler/CarController.java";

        // Génération du code
        codeGenerator.generateCode(program, outputPath);

        // Lecture et vérification du contenu généré
        String generatedCode = Files.readString(Paths.get(outputPath));
        assertTrue(generatedCode.contains("public class CarController"), "Le code généré doit contenir 'public class CarController'");
        assertTrue(generatedCode.contains("car.setX(car.getX() + 5);"), "Le code généré doit déplacer la voiture vers la droite de 5 unités");
    }
}
