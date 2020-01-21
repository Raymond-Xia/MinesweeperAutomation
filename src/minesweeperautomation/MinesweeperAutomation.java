package minesweeperautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MinesweeperAutomation {
    private WebDriver driver;
    private int x, y, adjs, flags;
    private String[] s;
    private Actions action;

    private boolean[][] doneGrid = new boolean[16][30];
    
    public static void main(String[] args){
        MinesweeperAutomation m = new MinesweeperAutomation();
        m.testClick();
    }
	
    public void testClick() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Raymond\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("http://www.minesweeperonline.com");

        driver.findElement(By.id("8_16")).click();
        
        for (int j = 0; j < 16; j++) {
            for (int i = 0; i < 30; i++) {
                doneGrid[j][i] = false;
            }
        }

        WebElement square;
        int num, rowCellsDone;

        while (!driver.findElements(By.className("blank")).isEmpty()) {
            for (int height = 1; height <= 16; height++) {
                for (int width = 1; width <= 30; width++) {
                    square = driver.findElement(By.id(height + "_" + width));
                    num = Character.getNumericValue(square.getAttribute("class").toCharArray()[11]);

                    if (num >= 0 && num < 9 && !doneGrid[height-1][width-1]) {
                        if (num == 0) {
                            doneGrid[height-1][width-1] = true;
                        } else if (countAdj(square) == num) {
                            flagAdj(square);
                            doneGrid[height-1][width-1] = true;
                        } else if (countFlags(square) == num) {
                            revealAdj(square);
                            doneGrid[height-1][width-1] = true;
                        }

                    }

                    System.out.println(height + "_" + width);
                }
            }
            for (int height = 16; height >= 1; height--) {
                for (int width = 30; width >= 1; width--) {
                    square = driver.findElement(By.id(height + "_" + width));
                    num = Character.getNumericValue(square.getAttribute("class").toCharArray()[11]);

                    if (num >= 0 && num < 9 && !doneGrid[height-1][width-1]) {
                        if (num == 0) {
                            doneGrid[height-1][width-1] = true;
                        } else if (countAdj(square) == num) {
                            flagAdj(square);
                            doneGrid[height-1][width-1] = true;
                        } else if (countFlags(square) == num) {
                            revealAdj(square);
                            doneGrid[height-1][width-1] = true;
                        }
                    }

                    System.out.println(height + "_" + width);
                }
            }
        } 
    }

    private int countAdj(WebElement e) {
        s = e.getAttribute("id").split("_");

        y = Integer.parseInt(s[0]);
        x = Integer.parseInt(s[1]);
        adjs = 0;

        if (y > 1) {
            if (x > 1) {
                if (!driver.findElement(By.id((y-1) + "_" + (x-1))).getAttribute("class").contains("open")) {
                    adjs++;
                }
            }
            if (!driver.findElement(By.id((y-1) + "_" + x)).getAttribute("class").contains("open")) {
                adjs++;
            }
            if (x < 30) {
                if (!driver.findElement(By.id((y-1) + "_" + (x+1))).getAttribute("class").contains("open")) {
                    adjs++;
                }
            }
        }
        if (x > 1) {
            if (!driver.findElement(By.id(y + "_" + (x-1))).getAttribute("class").contains("open")) {
                adjs++;
            }
        }
        if (x < 30) {
            if (!driver.findElement(By.id(y + "_" + (x+1))).getAttribute("class").contains("open")) {
                adjs++;	
            }
        }
        if (y < 16) {
            if (x > 1) {
                if (!driver.findElement(By.id((y+1) + "_" + (x-1))).getAttribute("class").contains("open")) {
                    adjs++;
                }
            }
            if (!driver.findElement(By.id((y+1) + "_" + x)).getAttribute("class").contains("open")) {
                adjs++;
            }
            if (x < 30) {
                if (!driver.findElement(By.id((y+1) + "_" + (x+1))).getAttribute("class").contains("open")) {
                    adjs++;
                }
            }
        }
        return adjs;
    }

    private int countFlags(WebElement e) {
        s = e.getAttribute("id").split("_");

        y = Integer.parseInt(s[0]);
        x = Integer.parseInt(s[1]);
        flags = 0;

        if (y > 1) {
            if (x > 1) {
                if (driver.findElement(By.id((y-1) + "_" + (x-1))).getAttribute("class").contains("bombflagged")) {
                    flags++;
                }
            }
            if (driver.findElement(By.id((y-1) + "_" + x)).getAttribute("class").contains("bombflagged")) {
                flags++;
            }
            if (x < 30) {
                if (driver.findElement(By.id((y-1) + "_" + (x+1))).getAttribute("class").contains("bombflagged")) {
                    flags++;
                }
            }
        }
        if (x > 1) {
            if (driver.findElement(By.id(y + "_" + (x-1))).getAttribute("class").contains("bombflagged")) {
                flags++;
            }
        }
        if (x < 30) {
            if (driver.findElement(By.id(y + "_" + (x+1))).getAttribute("class").contains("bombflagged")) {
                flags++;	
            }
        }
        if (y < 16) {
            if (x > 1) {
                if (driver.findElement(By.id((y+1) + "_" + (x-1))).getAttribute("class").contains("bombflagged")) {
                    flags++;
                }
            }
            if (driver.findElement(By.id((y+1) + "_" + x)).getAttribute("class").contains("bombflagged")) {
                flags++;
            }
            if (x < 30) {
                if (driver.findElement(By.id((y+1) + "_" + (x+1))).getAttribute("class").contains("bombflagged")) {
                    flags++;
                }
            }
        }
        return flags;
    }

    private void flagAdj(WebElement e) {
        s = e.getAttribute("id").split("_");
        y = Integer.parseInt(s[0]);
        x = Integer.parseInt(s[1]);

        action = new Actions(driver);

        if (y > 1) {
            if (x > 1) { 
                if (driver.findElement(By.id((y-1) + "_" + (x-1))).getAttribute("class").contains("blank")) {
                    action.contextClick(driver.findElement(By.id((y-1) + "_" + (x-1)))).perform();
                }
            }
            if (driver.findElement(By.id((y-1) + "_" + x)).getAttribute("class").contains("blank")) {
                action.contextClick(driver.findElement(By.id((y-1) + "_" + x))).perform(); 
            }
            if (x < 30) {
                if (driver.findElement(By.id((y-1) + "_" + (x+1))).getAttribute("class").contains("blank")) {
                    action.contextClick(driver.findElement(By.id((y-1) + "_" + (x+1)))).perform(); 
                }
            }
        }
        if (x > 1) {
            if (driver.findElement(By.id(y + "_" + (x-1))).getAttribute("class").contains("blank")) {
                action.contextClick(driver.findElement(By.id(y + "_" + (x-1)))).perform();
            }
        }
        if (x < 30) {
            if (driver.findElement(By.id(y + "_" + (x+1))).getAttribute("class").contains("blank")) {
                action.contextClick(driver.findElement(By.id(y + "_" + (x+1)))).perform();	
            }
        }
        if (y < 16) {
            if (x > 1) {
                if (driver.findElement(By.id((y+1) + "_" + (x-1))).getAttribute("class").contains("blank")) {
                    action.contextClick(driver.findElement(By.id((y+1) + "_" + (x-1)))).perform();
                }
            }
            if (driver.findElement(By.id((y+1) + "_" + x)).getAttribute("class").contains("blank")) {
                action.contextClick(driver.findElement(By.id((y+1) + "_" + x))).perform();
            }
            if (x < 30) {
                if (driver.findElement(By.id((y+1) + "_" + (x+1))).getAttribute("class").contains("blank")) {
                    action.contextClick(driver.findElement(By.id((y+1) + "_" + (x+1)))).perform();
                }
            }
        }
    }

    private void revealAdj(WebElement e) {
        s = e.getAttribute("id").split("_");
        y = Integer.parseInt(s[0]);
        x = Integer.parseInt(s[1]);
        WebElement element;

        if (y > 1) {
            if (x > 1) {
                if (driver.findElement(By.id((y-1) + "_" + (x-1))).getAttribute("class").contains("blank")) {
                    element = driver.findElement(By.id((y-1) + "_" + (x-1)));
                    element.click();
                }
            }
            if (driver.findElement(By.id((y-1) + "_" + x)).getAttribute("class").contains("blank")) {
                element = driver.findElement(By.id((y-1) + "_" + x));
                element.click();
            }
            if (x < 30) {
                if (driver.findElement(By.id((y-1) + "_" + (x+1))).getAttribute("class").contains("blank")) {
                    element = driver.findElement(By.id((y-1) + "_" + (x+1)));
                    element.click();
                }
            }
        }
        if (x > 1) {
            if (driver.findElement(By.id(y + "_" + (x-1))).getAttribute("class").contains("blank")) {
                element = driver.findElement(By.id(y + "_" + (x-1)));
                element.click();
            }
        }
        if (x < 30) {
            if (driver.findElement(By.id(y + "_" + (x+1))).getAttribute("class").contains("blank")) {
                element = driver.findElement(By.id(y + "_" + (x+1)));
                element.click();	
            }
        }
        if (y < 16) {
            if (x > 1) {
                if (driver.findElement(By.id((y+1) + "_" + (x-1))).getAttribute("class").contains("blank")) {
                    element = driver.findElement(By.id((y+1) + "_" + (x-1)));
                    element.click();
                }
            }
            if (driver.findElement(By.id((y+1) + "_" + x)).getAttribute("class").contains("blank")) {
                element = driver.findElement(By.id((y+1) + "_" + x));
                element.click();
            }
            if (x < 30) {
                if (driver.findElement(By.id((y+1) + "_" + (x+1))).getAttribute("class").contains("blank")) {
                    element = driver.findElement(By.id((y+1) + "_" + (x+1)));
                    element.click();
                }
            }
        }
    }
}
