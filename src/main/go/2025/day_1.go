package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type Instruction struct {
	Direction byte
	Distance  int
}

func parseInput(filename string) ([]Instruction, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var instructions []Instruction
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		if line == "" {
			continue
		}

		distance, err := strconv.Atoi(line[1:])
		if err != nil {
			return nil, fmt.Errorf("error parsing distance '%s': %v", line[1:], err)
		}

		instructions = append(instructions, Instruction{
			Direction: line[0],
			Distance:  distance,
		})
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return instructions, nil
}

func solve(instructions []Instruction) int {
	dialPos := 50
	zeroCount := 0

	for _, inst := range instructions {
		if inst.Direction == 'L' {
			dialPos = (dialPos - inst.Distance) % 100
			if dialPos < 0 {
				dialPos += 100
			}
		} else if inst.Direction == 'R' {
			dialPos = (dialPos + inst.Distance) % 100
		}

		if dialPos == 0 {
			zeroCount++
		}
	}
	return zeroCount
}

func main() {
	instructions, err := parseInput("../../resources/2025/day_1.txt")
	if err != nil {
		fmt.Printf("Error processing input: %v\n", err)
		return
	}

	result := solve(instructions)
	fmt.Printf("Password: %d\n", result)
}
