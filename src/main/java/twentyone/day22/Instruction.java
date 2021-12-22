package twentyone.day22;

public record Instruction(
        boolean isOn,
        Range x,
        Range y,
        Range z
        ) {
}
