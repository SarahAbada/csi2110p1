# CSI2110 Programming Assignment 1

## Overview
This project implements partition-based algorithms for analyzing island and lake formations in grid data. It now includes a **graphical user interface (GUI)** with a login page and interactive UI for data analysis.

## Features

### 🖥️ Graphical User Interface (NEW!)
- **Login Page**: Secure authentication interface
- **Main UI**: Interactive split-pane interface for data analysis
- **File Loading**: Load data from files via file chooser dialog
- **Real-time Analysis**: Analyze island formations with visual feedback
- **Menu System**: File and Help menus for easy navigation

### 📊 Core Functionality
- Island detection and counting
- Lake identification
- Area calculations
- Partition-based clustering using union-find data structure

## Quick Start

### Running the GUI Application
```bash
# Compile the UI components
javac LoginPage.java MainUI.java

# Launch the application (starts with login page)
java LoginPage
```

**Login Credentials:**
- Username: `admin`
- Password: `password123`

### Running Command-Line Version
```bash
# Compile and run
javac IslandLakeSurvey.java
java IslandLakeSurvey < input1.txt
```

### Running Tests
```bash
# Run partition tests
javac TestPartition.java && java TestPartition

# Run UI tests
javac TestUI.java && java TestUI
```

## Documentation
- **UI_README.md**: Detailed GUI user guide and technical documentation
- Input files: `input1.txt` through `input6.txt` with corresponding output files

## Project Structure
- `LoginPage.java`: Authentication interface with credential validation
- `MainUI.java`: Main application UI with analysis capabilities
- `Partition.java`: Generic partition data structure implementation
- `Cluster.java`: Cluster representation for partition elements
- `Node.java`: Node data structure for partition elements
- `Sequence.java`: Doubly-linked list sequence implementation
- `IslandLakeSurvey.java`: Command-line interface for island/lake analysis
- `TestPartition.java`: Unit tests for partition operations
- `TestUI.java`: UI component verification tests

## Requirements
- Java 17 or higher
- Java Swing (included in JDK)
- No external dependencies required
