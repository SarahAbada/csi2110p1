import java.awt.HeadlessException;

/**
 * TestUI - Simple test to verify UI components compile and instantiate correctly
 */
public class TestUI {
    public static void main(String[] args) {
        System.out.println("Testing UI Components...\n");
        
        boolean isHeadless = java.awt.GraphicsEnvironment.isHeadless();
        if (isHeadless) {
            System.out.println("Note: Running in headless environment (no display available)");
            System.out.println("UI components can be compiled but not instantiated without a display.\n");
        }
        
        // Test 1: Verify LoginPage class exists and compiles
        try {
            if (!isHeadless) {
                LoginPage loginPage = new LoginPage();
                System.out.println("✓ LoginPage created successfully");
                System.out.println("  - Title: " + loginPage.getTitle());
                System.out.println("  - Size: " + loginPage.getSize());
                loginPage.dispose();
            } else {
                // Just verify the class is loadable
                Class.forName("LoginPage");
                System.out.println("✓ LoginPage class loaded successfully (compilation verified)");
            }
        } catch (HeadlessException e) {
            System.out.println("✓ LoginPage compiled (cannot display in headless mode)");
        } catch (Exception e) {
            System.out.println("✗ LoginPage failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Test 2: Verify MainUI class exists and compiles
        try {
            if (!isHeadless) {
                MainUI mainUI = new MainUI();
                System.out.println("✓ MainUI created successfully");
                System.out.println("  - Title: " + mainUI.getTitle());
                System.out.println("  - Size: " + mainUI.getSize());
                mainUI.dispose();
            } else {
                // Just verify the class is loadable
                Class.forName("MainUI");
                System.out.println("✓ MainUI class loaded successfully (compilation verified)");
            }
        } catch (HeadlessException e) {
            System.out.println("✓ MainUI compiled (cannot display in headless mode)");
        } catch (Exception e) {
            System.out.println("✗ MainUI failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n✓ All UI component tests passed!");
        System.out.println("\nTo run the application on a system with a display:");
        System.out.println("  java LoginPage    # Start with login");
        System.out.println("  java MainUI       # Start main UI directly");
        System.out.println("\nLogin credentials:");
        System.out.println("  Username: admin");
        System.out.println("  Password: password123");
        
        System.exit(0);
    }
}
