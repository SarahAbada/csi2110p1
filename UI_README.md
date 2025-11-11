# Island Lake Survey UI - User Guide

## Overview
This application now includes a graphical user interface (GUI) with a login page and main application UI for analyzing island and lake data.

## Features

### 1. Login Page (LoginPage.java)
- Secure authentication interface
- Username/password validation
- Clean, user-friendly design
- Automatic transition to main UI upon successful login

**Login Credentials:**
- Username: `admin`
- Password: `password123`

### 2. Main Application UI (MainUI.java)
- Interactive interface for island/lake analysis
- File loading capability via file chooser dialog
- Split-pane layout with input and output areas
- Menu system (File, Help)
- Real-time data analysis
- Statistical output display

## How to Use

### Running the Application

```bash
# Compile the UI components
javac LoginPage.java MainUI.java

# Launch the application (starts with login page)
java LoginPage

# Or launch main UI directly (for testing)
java MainUI
```

### Using the Login Page
1. Launch the application
2. Enter username: `admin`
3. Enter password: `password123`
4. Click "Login" button (or press Enter)
5. Upon successful authentication, the main UI will open

### Using the Main UI

#### Loading Data
- **Option 1:** Type or paste island data directly into the "Input Data" text area
- **Option 2:** Click "Load File" button and select an input file (e.g., input1.txt, input2.txt)

#### Analyzing Data
1. Ensure data is loaded in the input area
2. Click "Analyze Data" button
3. View results in the "Analysis Results" panel

#### Input Data Format
```
rows cols
grid_data (0=water, 1=land)

Example:
4 5
0 1 1 0 0
1 1 0 0 1
0 0 0 1 1
0 1 0 0 0
```

#### Analysis Results Include:
- Grid dimensions
- Number of islands
- Island sizes (sorted in descending order)
- Total land area
- Average island size
- Total number of clusters

### Menu Options
- **File → Load File:** Open a file chooser to load data
- **File → Exit:** Close the application
- **Help → About:** Display application information and credentials

## Technical Details

### Classes
- **LoginPage.java:** Authentication interface using Java Swing
  - JFrame-based GUI with username/password fields
  - Event handling for login/cancel actions
  - Integration with MainUI

- **MainUI.java:** Main application interface
  - Split-pane layout for input/output
  - File I/O operations
  - Integration with Partition, Cluster, and Node classes
  - Menu bar and button controls
  - Real-time data analysis

### Dependencies
- Java 17 or higher
- Java Swing (included in JDK)
- Existing classes: Partition, Cluster, Node, Sequence

## Example Workflow

1. Start application: `java LoginPage`
2. Login with admin/password123
3. Main UI opens automatically
4. Click "Load File" and select `input1.txt`
5. Click "Analyze Data"
6. View island count, sizes, and statistics in output panel
7. Load different files and analyze as needed
8. Use "Clear" to reset both panels

## Benefits
- **User-Friendly:** No need to use command-line input
- **Visual Feedback:** Immediate display of analysis results
- **Flexible Input:** Support for both file loading and direct text input
- **Secure:** Login authentication before accessing the analyzer
- **Professional:** Clean, modern GUI with proper layout and controls

## Screenshots Description

### Login Page
- Clean, centered login form
- Title: "Island Lake Survey"
- Username and password fields
- Login and Cancel buttons
- Error messages for invalid credentials

### Main UI
- Top: Application title "Island Lake Survey Analyzer"
- Left panel: "Input Data" with text area and "Load File" button
- Right panel: "Analysis Results" with read-only output display
- Bottom: Control buttons (Analyze Data, Clear, About)
- Menu bar: File and Help menus
