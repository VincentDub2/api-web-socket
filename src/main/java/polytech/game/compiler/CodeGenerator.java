package polytech.game.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CodeGenerator {

    private final String className = "CarController";

    public void generateCode(Parser.Program program, String outputPath) throws IOException {
        StringBuilder classContent = new StringBuilder();

        classContent.append("package polytech.game.compiler;\n\n");
        classContent.append("import polytech.game.models.Car;\n\n");
        classContent.append("public class ").append(className).append(" {\n\n");

        for (int i = 0; i < program.commands.size(); i++) {
            Parser.Command command = program.commands.get(i);
            classContent.append("    public void moveCar").append(i).append("(Car car) {\n");
            switch (command.action) {
                case "right" ->
                        classContent.append("        car.setX(car.getX() + ").append(command.value).append(");\n");
                case "left" ->
                        classContent.append("        car.setX(car.getX() - ").append(command.value).append(");\n");
                case "up" ->
                        classContent.append("        car.setY(car.getY() - ").append(command.value).append(");\n");
                case "down" ->
                        classContent.append("        car.setY(car.getY() + ").append(command.value).append(");\n");
            }
            classContent.append("    }\n\n");
        }
        classContent.append("}\n\n");
        // Voici la partie ajoutée pour écrire le contenu dans un fichier
        Files.writeString(Paths.get(outputPath), classContent.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}