package main

import "testing"

func TestSolve(t *testing.T) {
	instructions, err := parseInput("../../resources/2025/day_1.txt")
	if err != nil {
		t.Fatalf("Failed to parse input: %v", err)
	}

	expected := 1031
	result := solve(instructions)
	if result != expected {
		t.Errorf("Expected %d, got %d", expected, result)
	}
}
