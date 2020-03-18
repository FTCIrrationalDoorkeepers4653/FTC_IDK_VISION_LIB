# FTC_IDK_VISION_LIB
### Simple and Easy to Use FTC Vision Pipeline System

## Installation Instructions:

### 1. Download the zip…Extract All to a chosen Location
### 2. Open FTC project in Android Studio (no OnBot Java :[ )...Create A New Module and Name it
### 3. Add src -> main -> java -> lib Package of FTC_IDK_VISION_LIB to the Newly Created Module
### 4. Add Source Package of TeamCode-Files to your Team Code -> src -> main -> java -> org -> firstinspires -> ftc -> teamcode package
### 5. In Android Studio, go to Gradle Scripts -> TeamCode-buld.release.gradle -> add the line implements project (‘:name of module’) -> Sync Gradle
   
## Usage Instructions:
  
### Sample Pipeline:
  public boolean sampleLeftPipeline() {
    //Gets RGB Array For Analysis-PARAMS: (Resize Ratio, startX, startY, width of area to analyze, height of area to analyze)
    int leftRGBArray[][] = imageInit.getRGBArray(0.1, 12, 20, 40, 32);
    
    //Analyzes Selected Portion of Image (using RGB above)-PARAMS: (rgbValues 2D Array, pixelMargin for lighting, number of pixels counted before classified)
    boolean foundLeft = imageInit.detectObject(leftRGBArray, 25, 10);

    //Returning the Value:
    return foundLeft;
  }

### See CustomVuforia_Test.java in TeamCode-Files, for more information.
