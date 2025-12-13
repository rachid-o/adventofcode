package main

import "testing"

func TestPart1(t *testing.T) {
	instructions, err := parseInput("../../resources/2025/day_1.txt")
	if err != nil {
		t.Fatalf("Failed to parse input: %v", err)
	}

	expected := 1031
	result := part1(instructions)
	if result != expected {
		t.Errorf("Expected %d, got %d", expected, result)
	}
}

func TestPart2(t *testing.T) {
	// Example from the problem description
	instructions := []Instruction{
		{Direction: 'L', Distance: 68},
		{Direction: 'L', Distance: 30},
		{Direction: 'R', Distance: 48},
		{Direction: 'L', Distance: 5},
		{Direction: 'R', Distance: 60},
		{Direction: 'L', Distance: 55},
		{Direction: 'L', Distance: 1},
		{Direction: 'L', Distance: 99},
		{Direction: 'R', Distance: 14},
		{Direction: 'L', Distance: 82},
	}

	expected := 6
	result := part2(instructions)
	if result != expected {
		t.Errorf("Expected %d, got %d", expected, result)
	}
}
