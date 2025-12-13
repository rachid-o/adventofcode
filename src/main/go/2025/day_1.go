package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	// file, err := os.Open("src/main/go/2025/puzzle-input.txt")
	file, err := os.Open("day_1.txt")
	if err != nil {
		fmt.Printf("Error opening file: %v\n", err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	dialPos := 50
	zeroCount := 0

	for scanner.Scan() {
		line := scanner.Text()
		line = strings.TrimSpace(line)
		if line == "" {
			continue
		}

		direction := line[0]
		distanceStr := line[1:]
		distance, err := strconv.Atoi(distanceStr)
		if err != nil {
			fmt.Printf("Error parsing distance '%s': %v\n", distanceStr, err)
			continue
		}

		if direction == 'L' {
			dialPos = (dialPos - distance) % 100
			if dialPos < 0 {
				dialPos += 100
			}
		} else if direction == 'R' {
			dialPos = (dialPos + distance) % 100
		}

		if dialPos == 0 {
			zeroCount++
		}
	}

	if err := scanner.Err(); err != nil {
		fmt.Printf("Error reading file: %v\n", err)
	}

	fmt.Printf("Password: %d\n", zeroCount)
}
