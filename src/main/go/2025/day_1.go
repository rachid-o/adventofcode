package main

import (
	"bufio"
	"fmt"
	"log"
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

func part1(instructions []Instruction) int {
	dialPos := 50
	zeroCount := 0

	for _, inst := range instructions {
		switch inst.Direction {
		case 'L':
			dialPos = (dialPos - inst.Distance) % 100
			if dialPos < 0 {
				dialPos += 100
			}
		case 'R':
			dialPos = (dialPos + inst.Distance) % 100
		}

		if dialPos == 0 {
			zeroCount++
		}
	}
	return zeroCount
}

func part2(instructions []Instruction) int {
	dialPos := 50
	zeroCount := 0

	for _, inst := range instructions {
		for i := 0; i < inst.Distance; i++ {
			switch inst.Direction {
			case 'L':
				dialPos--
				if dialPos < 0 {
					dialPos = 99
				}
			case 'R':
				dialPos++
				if dialPos > 99 {
					dialPos = 0
				}
			}

			if dialPos == 0 {
				zeroCount++
			}
		}
	}
	return zeroCount
}

func main() {
	wd, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Working directory:", wd)

	instructions, err := parseInput("../resources/2025/day_1.txt")
	//instructions, err := parseInput("../../resources/2025/day_1.txt")
	if err != nil {
		log.Printf("Error processing input: %v\n", err)
		return
	}

	result1 := part1(instructions)
	log.Printf("Password Part 1: %d\n", result1)

	result2 := part2(instructions)
	log.Printf("Password Part 2: %d\n", result2)
}
